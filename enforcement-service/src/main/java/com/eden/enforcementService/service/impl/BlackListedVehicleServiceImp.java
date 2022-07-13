package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.mapper.BlackListedVehicleMapper;
import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import com.eden.enforcementService.common.model.enums.VehicleSource;
import com.eden.enforcementService.common.request.BlackListedVehicleRequest;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.producers.vehicle.VehicleProducer;
import com.eden.enforcementService.producers.vehicle.dtos.VehicleDto;
import com.eden.enforcementService.repository.BlackListedVehicleRepository;
import com.eden.enforcementService.service.BlackListedVehicleService;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class BlackListedVehicleServiceImp implements BlackListedVehicleService {

    @Autowired
    private BlackListedVehicleRepository blackListedVehicleRepository;

    @Autowired
    private BlackListedVehicleMapper blackListedVehicleMapper;

    @Autowired
    private VehicleProducer vehicleProducer;

    @Override
    public String addBlackListedVehicle(BlackListedVehicleRequest blackListedVehicleRequest) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        BlackListedVehicle existed = blackListedVehicleRepository.findByPlateNumberArAndPlateNumberEnAndCountryId(blackListedVehicleRequest.getPlateNumberAr(),
                blackListedVehicleRequest.getPlateNumberEn(),
                blackListedVehicleRequest.getCountryId());
        if (Objects.nonNull(existed)) {
            if (!existed.isDeleted()) {
                return Constants.SuccessMessages.BLACK_LIST_VEHICLE_EXISTS;
            } else {
                updateBlackListedVehicle(existed.getId(), blackListedVehicleRequest);
                return Constants.SuccessMessages.BLACK_LIST_VEHICLE_WAS_DELETED_AND_ADDED_AGAIN;
            }
        }
        BlackListedVehicle blackListedVehicle = blackListedVehicleMapper.map(blackListedVehicleRequest);
        blackListedVehicle.setCreatedBy(userName);
        blackListedVehicleRepository.save(blackListedVehicle);
        VehicleDto vehicleDto = blackListedVehicleMapper.fromBlackToVehicleDto(blackListedVehicleRequest);
        vehicleDto.setCreatedBy(userName);
        vehicleDto.setSource(VehicleSource.BLACKLIST);
        try {
            vehicleProducer.publishNewVehicle(vehicleDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return Constants.SuccessMessages.BLACK_LIST_VEHICLE;
    }

    @Override
    public PaginationDto searchBlackListedVehicles(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());
        Page<BlackListedVehicle> result = keyword == null ? blackListedVehicleRepository.searchBlackListedVehicle(pageable) :
                blackListedVehicleRepository.searchBlackListedVehicle(keyword.toLowerCase(), pageable);

        return PaginationDto.builder().
                totalElements(result.getTotalElements()).
                content(result.getContent()).
                totalPages(result.getTotalPages()).
                build();
    }

    @Override
    public BlackListedVehicle getActiveBlackListedVehicleById(Long id) {
        BlackListedVehicle blackListedVehicle = getBlackListedVehicleById(id);
        if (blackListedVehicle.isDeleted()) {
            throw new BusinessException(Constants.ErrorKeys.BLACK_LIST_VEHICLE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return blackListedVehicle;
    }

    @Override
    public BlackListedVehicle getBlackListedVehicleById(Long id) {
        if (Objects.isNull(id))
            throw new BusinessException(Constants.ErrorKeys.BLACK_LIST_VEHICLE_NOT_FOUND, HttpStatus.BAD_REQUEST);

        Optional<BlackListedVehicle> blackListedVehicle = this.blackListedVehicleRepository.findById(id);

        if (blackListedVehicle.isEmpty()) {
            throw new BusinessException(Constants.ErrorKeys.BLACK_LIST_VEHICLE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        return blackListedVehicle.get();
    }

    @Override
    public BlackListedVehicle getBlackListedVehicleByEnPlateNumberAndCountryName(String enPlateNumber, String countyName) {
        BlackListedVehicle blackListedVehicle = blackListedVehicleRepository.findByPlateNumberEnAndCountryName(enPlateNumber, countyName);
        if (Objects.isNull(blackListedVehicle) || blackListedVehicle.isDeleted()) {
            return null;
        }
        return blackListedVehicle;
    }

    @Override
    public String deleteBlackListedVehicle(Long id) {
        BlackListedVehicle blackListVehicle = getActiveBlackListedVehicleById(id);
        blackListVehicle.setDeleted(true);
        blackListVehicle.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        blackListedVehicleRepository.save(blackListVehicle);
        return Constants.SuccessMessages.BLACK_LIST_VEHICLE_DELETED;
    }

    @Override
    public String updateBlackListedVehicle(Long id, BlackListedVehicleRequest blackListedVehicleRequest) {
        BlackListedVehicle blackListVehicle = getBlackListedVehicleById(id);
        blackListVehicle.setPlateNumberAr(blackListedVehicleRequest.getPlateNumberAr());
        blackListVehicle.setPlateNumberEn(blackListedVehicleRequest.getPlateNumberEn());
        blackListVehicle.setCountryId(blackListedVehicleRequest.getCountryId());
        blackListVehicle.setColorId(blackListedVehicleRequest.getColorId());
        blackListVehicle.setMakeId(blackListedVehicleRequest.getMakeId());
        blackListVehicle.setModel(blackListedVehicleRequest.getModel());
        blackListVehicle.setType(blackListedVehicleRequest.getType());
        blackListVehicle.setDescription(blackListedVehicleRequest.getDescription());
        blackListVehicle.setUserName(blackListedVehicleRequest.getUserName());
        blackListVehicle.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        blackListVehicle.setModifiedDate(LocalDateTime.now());
        blackListVehicle.setDeleted(blackListedVehicleRequest.isDeleted());
        blackListedVehicleRepository.save(blackListVehicle);
        return Constants.SuccessMessages.BLACK_LIST_VEHICLE_DELETED;
    }
}
