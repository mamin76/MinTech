package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.mapper.PenaltyMapper;
import com.eden.enforcementService.common.model.entity.BlackListPenalty;
import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.request.BlackListPenaltyRequest;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.repository.BlackListPenaltyRepository;
import com.eden.enforcementService.service.BlackListPenaltiesService;
import com.eden.enforcementService.service.BlackListedVehicleService;
import com.eden.enforcementService.service.PenaltyService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BlackListPenaltiesServiceImp implements BlackListPenaltiesService {

    @Autowired
    private BlackListedVehicleService blackListedVehicleService;

    @Autowired
    private PenaltyService penaltyService;

    @Autowired
    private BlackListPenaltyRepository blackListPenaltyRepository;

    @Autowired
    private PenaltyMapper penaltyMapper;

    @Override
    public void addBlackListPenalties(BlackListPenaltyRequest blackListPenaltyRequest) {
        Set<Long> penaltiesIdsSet = validateBlackListPenalties(blackListPenaltyRequest);

        BlackListedVehicle blackListedVehicle = blackListedVehicleService.getActiveBlackListedVehicleById(blackListPenaltyRequest.getBlackListedId());

        penaltiesIdsSet.forEach(p -> {
            BlackListPenalty blackListPenalty = new BlackListPenalty();
            Penalty penalty = new Penalty(p);
            boolean exists = blackListPenaltyRepository.existsByBlackListedVehicleAndPenalty(blackListedVehicle, penalty);
            if (!exists) {
                blackListPenalty.setPenalty(penalty);
                blackListPenalty.setBlackListedVehicle(blackListedVehicle);
                blackListPenalty.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
                blackListPenaltyRepository.save(blackListPenalty);
            }
        });
    }

    private Set<Long> validateBlackListPenalties(BlackListPenaltyRequest blackListPenaltyRequest) {
        List<Long> penaltiesIds = blackListPenaltyRequest.getPenaltiesIds();
        Set<Long> penaltiesIdsSet = new HashSet<>(penaltiesIds);
        if (penaltiesIdsSet.size() < penaltiesIds.size()) {
            throw new BusinessException(Constants.ErrorKeys.DUPLICATE_IN_DATA, HttpStatus.BAD_REQUEST);
        }

        int penaltiesCount = penaltyService.countPenaltiesByIds(penaltiesIdsSet);
        if (penaltiesCount < penaltiesIdsSet.size()) {
            throw new BusinessException(Constants.ErrorKeys.SOME_PENALTIES_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return penaltiesIdsSet;
    }

    @Transactional
    @Override
    public void updateBlackListPenalties(BlackListPenaltyRequest blackListPenaltyRequest) {
        validateBlackListPenalties(blackListPenaltyRequest);
        blackListPenaltyRepository.deletedAndSetLogInfo(SecurityContextHolder.getContext().getAuthentication().getName(),
                LocalDateTime.now(), blackListPenaltyRequest.getBlackListedId());
        addBlackListPenalties(blackListPenaltyRequest);
    }

    @Override
    public PaginationDto getPenaltiesByBlackListId(Long blackListId) {
        List<BlackListPenalty> blackListPenalties = blackListPenaltyRepository.getBlackListPenaltyByBlackListedVehicle(new BlackListedVehicle(blackListId));
        List<Penalty> penalties = new LinkedList<>();
        blackListPenalties.forEach(blackListPenalty -> {
            penalties.add(blackListPenalty.getPenalty());
        });
        List<PenaltyDto> penaltiesDtos = penaltyMapper.toDto(penalties);
        return PaginationDto.builder()
                .content(penaltiesDtos)
                .totalPages(1)
                .totalElements(Long.valueOf(penaltiesDtos.size()))
                .build();
    }
}
