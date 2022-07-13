package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.dto.ViolationDetailsDto;
import com.eden.enforcementService.common.dto.ViolationDto;
import com.eden.enforcementService.common.mapper.PenaltyMapper;
import com.eden.enforcementService.common.mapper.ViolationMapper;
import com.eden.enforcementService.common.model.entity.Violation;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.request.ViewViolationsRequest;
import com.eden.enforcementService.common.request.ViolationRequest;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.repository.OperationViolationPenaltiesRepository;
import com.eden.enforcementService.repository.ViolationRepository;
import com.eden.enforcementService.service.ViolationService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Primary
public class ViolationServiceImp implements ViolationService {
    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private ViolationMapper violationMapper;
    @Autowired
    private PenaltyMapper penaltyMapper;
    @Autowired
    private OperationViolationPenaltiesRepository operationViolationPenaltiesRepository;


    @Override
    public void addViolation(ViolationRequest violationRequest) {
        if(violationRepository.existsByEnNameOrArName(
                violationRequest.getEnName(),violationRequest.getArName())){
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_EXIST, HttpStatus.BAD_REQUEST);
        }
        Violation violation = violationMapper.toEntity(violationRequest);
        violationRepository.save(violation);
    }

    @Override
    public List<ViolationDto> getViolations() {
        List<Violation> violations = violationRepository.findAll();
        return violationMapper.toDto(violations);
    }

    @Transactional
    @Override
    public ViolationDetailsDto getViolationDetails(Long violationId, Long operationId) {
        if (!violationRepository.existsById(violationId)) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Violation violation = violationRepository.getById(violationId);
        if (operationViolationPenaltiesRepository
                .existsByOperationIdAndViolation(operationId, violation)) {
            List<PenaltyDto> violationPenalties = new ArrayList<>();
            violation.getOperationViolationPenalties().forEach(operationViolationPenalties -> {
                if (Objects.equals(operationViolationPenalties.getOperationId(), operationId)) {
                    PenaltyDto penaltyDto = penaltyMapper.toDto(operationViolationPenalties.getPenalty());
                    violationPenalties.add(penaltyDto);
                }
            });
            double violationFees =
                    violationPenalties.stream().mapToDouble(PenaltyDto::getFees).sum();

            return ViolationDetailsDto.builder()
                    .id(violation.getId()).arName(violation.getArName())
                    .enName(violation.getEnName()).enDescription(violation.getEnDescription())
                    .arDescription(violation.getArDescription())
                    .violationFees(violationFees).penaltyList(violationPenalties).build();
        } else {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND_IN_OPERATION, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Long countViolationByIds(List<Long> violationIds) {
        return violationRepository.countViolationByIds(violationIds);
    }

    @Override
    public PaginationDto searchViolations(ViewViolationsRequest viewViolationsRequest) {
        Pageable pageable = PageRequest.of(viewViolationsRequest.getPage() - 1, viewViolationsRequest.getSize(),
                Sort.Direction.valueOf(viewViolationsRequest.getSortDirection()), viewViolationsRequest.getSortBy());
        Page<Violation> page = null;
        if (Objects.nonNull(viewViolationsRequest.getQuery())) {
            page = violationRepository.searchViolations(viewViolationsRequest.getQuery(), pageable);
        } else {
            page = violationRepository.listViolationsPagination(pageable);
        }
        return PaginationDto.builder().
                totalElements(page.getTotalElements()).
                content(violationMapper.toDto(page.getContent())).
                totalPages(page.getTotalPages()).
                build();

    }

    @Override
    public String deleteViolation(Long id) {
        Violation Violation = getViolationById(id);
        Violation.setDeleted(true);
        Violation.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        violationRepository.save(Violation);
        return Constants.SuccessMessages.VIOLATION_DELETED;
    }

    @Override
    public Violation getViolationById(Long id) {
        if (Objects.isNull(id))
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND, HttpStatus.BAD_REQUEST);

        Optional<Violation> violation = this.violationRepository.findById(id);

        if (!violation.isPresent()) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND, HttpStatus.BAD_REQUEST);

        }
        return violation.get();
    }

    @Override
    public ViolationDto getViolationsById(Long id) {
        Violation violation = getViolationById(id);
        return violationMapper.toDto(violation);
    }

    @Override
    public String updateViolation(Long id, ViolationRequest violationRequest) {
        Violation violation = getViolationById(id);
        violation.setArName(violationRequest.getArName());
        violation.setEnName(violationRequest.getEnName());
        violation.setEnDescription(violationRequest.getEnDescription());
        violation.setArDescription(violationRequest.getArDescription());
        violation.setModifiedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        violation.setModifiedDate(LocalDateTime.now());
        violationRepository.save(violation);
        return Constants.SuccessMessages.VIOLATION_UPDATED;
    }


    @Override
    public Boolean existByName(String name, LanguageEnum lang) {
        if(lang==LanguageEnum.en){
            return violationRepository.existsByEnName(name);
        }else if(lang==LanguageEnum.ar){
            return violationRepository.existsByArName(name);
        }
        return null;
    }

}
