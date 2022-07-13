package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.mapper.PenaltyMapper;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.model.entity.Violation;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import com.eden.enforcementService.common.request.PenaltyRequest;
import com.eden.enforcementService.common.request.ViewPenaltyRequest;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.repository.PenaltyRepository;
import com.eden.enforcementService.service.PenaltyService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Primary
public class PenaltyServiceImp implements PenaltyService {
    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private PenaltyMapper penaltyMapper;

    @Override
    public void addPenalties(PenaltyRequest penaltyRequest) {
        if(penaltyRepository.existsByEnNameOrArName(
                penaltyRequest.getEnName(),penaltyRequest.getArName())){
            throw new BusinessException(Constants.ErrorKeys.PENALTY_EXIST, HttpStatus.BAD_REQUEST);
        }
        Penalty penalty = penaltyMapper.toEntity(penaltyRequest);
        penaltyRepository.save(penalty);
    }

    @Override
    public void editPenalties(PenaltyRequest penaltyRequest) {
        Penalty penalty = penaltyMapper.toEntity(penaltyRequest);
        penaltyRepository.save(penalty);
    }

    @Override
    public List<PenaltyDto> getPenalties() {
        List<Penalty> penalties = penaltyRepository.findAll();
        return penaltyMapper.toDto(penalties);
    }

    @Override
    public PaginationDto searchPagePenalties(ViewPenaltyRequest viewPenaltyRequest) {
        int page = viewPenaltyRequest.getPage();
        int size = viewPenaltyRequest.getSize();
        String searchValue=viewPenaltyRequest.getSearchValue();
        PenaltyType typeValue=viewPenaltyRequest.getTypeValue();
        PenaltyMethod methodValue=viewPenaltyRequest.getMethodValue();
        String typeStr= typeValue==null?"":typeValue.name();
        String methodStr= methodValue==null?"":methodValue.name();
        Pageable pageable = PageRequest.of(page, size);
        Page<Penalty> penaltiesPage =
                penaltyRepository.findAllBySearchValue(searchValue,typeStr,methodStr,pageable);
        return PaginationDto.builder().
                totalElements(penaltiesPage.getTotalElements()).
                content(penaltyMapper.toDto(penaltiesPage.getContent())).
                totalPages(penaltiesPage.getTotalPages()).
                build();
    }

    @Override
    public void deletePenalties(Long penaltyId) {
        boolean exist = penaltyRepository.existsById(penaltyId);
        if(!exist){
            throw new BusinessException(Constants.ErrorKeys.PENALTY_NOT_FOUND,
                    HttpStatus.BAD_REQUEST);
        }
        penaltyRepository.deleteById(penaltyId);
    }

    @Override
    public PenaltyDto getPenaltyDetails(Long penaltyId) {
        boolean exist = penaltyRepository.existsById(penaltyId);
        if(!exist){
            throw new BusinessException(Constants.ErrorKeys.PENALTY_NOT_FOUND,
                    HttpStatus.BAD_REQUEST);
        }
        Penalty penalty = penaltyRepository.findById(penaltyId).get();
        return penaltyMapper.toDto(penalty);
    }

    @Override
    public Integer countPenaltiesByIds(Collection<Long> ids) {
        return penaltyRepository.countByIdIn(ids);
    }

    @Override
    public PenaltyDto getPenaltyByEnName(String enName) {
        return penaltyMapper.toDto(penaltyRepository.findByEnName(enName));
    }

    @Override
    public Boolean existByName(String name, LanguageEnum lang) {
        if(lang==LanguageEnum.en){
            return penaltyRepository.existsByEnName(name);
        }else if(lang==LanguageEnum.ar){
            return penaltyRepository.existsByArName(name);
        }
        return null;
    }
}
