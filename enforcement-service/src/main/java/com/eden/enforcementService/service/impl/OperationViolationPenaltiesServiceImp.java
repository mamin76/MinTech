package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.common.dto.ManageViolationDto;
import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.ViolationDto;
import com.eden.enforcementService.common.mapper.ViolationMapper;
import com.eden.enforcementService.common.model.entity.OperationViolationPenalties;
import com.eden.enforcementService.common.model.entity.OperationViolationPenaltiesLifecycle;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.model.entity.Violation;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.request.OperationViolationPenaltiesRequest;
import com.eden.enforcementService.common.request.OperationViolationSearchRequest;
import com.eden.enforcementService.common.request.UpdateOVPRequest;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.repository.OperationViolationPenaltiesLifecycleRepository;
import com.eden.enforcementService.repository.OperationViolationPenaltiesRepository;
import com.eden.enforcementService.repository.PenaltyRepository;
import com.eden.enforcementService.repository.ViolationRepository;
import com.eden.enforcementService.service.OperationViolationPenaltiesService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@Primary
public class OperationViolationPenaltiesServiceImp implements OperationViolationPenaltiesService {
    @Autowired
    private OperationViolationPenaltiesRepository operationViolationPenaltiesRepository;
    @Autowired
    private OperationViolationPenaltiesLifecycleRepository operationViolationPenaltiesLifecycleRepository;
    @Autowired
    private PenaltyRepository penaltyRepository;
    @Autowired
    private ViolationRepository violationRepository;
    @Autowired
    private ViolationMapper violationMapper;


    @Override
    public void linkOperationViolationPenalties(OperationViolationPenaltiesRequest
                                                        operationViolationPenaltiesRequest) {
        Long operationId = operationViolationPenaltiesRequest.getOperationId();
        List<Long> penaltiesIds = operationViolationPenaltiesRequest.getPenaltiesIds();
        // TODO
        List<LocalDate> startDates = operationViolationPenaltiesRequest.getStartDates();
        List<LocalDate> endDates = operationViolationPenaltiesRequest.getEndDates();
        Set<Long> set = new HashSet<>(penaltiesIds);
        if (set.size() < penaltiesIds.size()) {
            /* There are duplicates */
            throw new BusinessException(Constants.ErrorKeys.DUPLICATE_IN_DATA, HttpStatus.BAD_REQUEST);
        }
        Long violationId = operationViolationPenaltiesRequest.getViolationId();
        if (!violationRepository.existsById(violationId)) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Violation violation = violationRepository.getById(violationId);
        if (operationViolationPenaltiesRepository
                .existsByOperationIdAndViolationAndDeletedFalse(operationId, violation)) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_OPERATION_EXIST, HttpStatus.BAD_REQUEST);
        }
        // TODO
        for (int i = 0; i < penaltiesIds.size(); i++) {
            if (!penaltyRepository.existsById(penaltiesIds.get(i))) {
                throw new BusinessException(Constants.ErrorKeys.PENALTY_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            Penalty penalty = penaltyRepository.getById(penaltiesIds.get(i));
            //save OperationViolationPenalties
            OperationViolationPenalties operationViolationPenalties =
                    OperationViolationPenalties.builder().violation(violation).penalty(penalty)
                            .operationId(operationId).build();
            // TODO end date not null set deleted
            if (Objects.nonNull(endDates) && endDates.get(i) != null) {
                operationViolationPenalties.setDeleted(true);
            }
            operationViolationPenalties = operationViolationPenaltiesRepository.save(operationViolationPenalties);
            //save OperationViolationPenaltiesLifecycle
            OperationViolationPenaltiesLifecycle operationViolationPenaltiesLifecycle =
                    OperationViolationPenaltiesLifecycle.builder()
                            .operationViolationPenalty(operationViolationPenalties)
                            .startDate(startDates == null ? LocalDate.now() : startDates.get(i))
                            .endDate((endDates == null || Objects.isNull(endDates.get(i))) ? null : endDates.get(i))
                            .comment(null).build();
            operationViolationPenaltiesLifecycleRepository
                    .save(operationViolationPenaltiesLifecycle);
        }
    }

    @Override
    public void updateOperationViolationPenalties(OperationViolationPenaltiesRequest
                                                          operationViolationPenaltiesRequest) {
        Long operationId = operationViolationPenaltiesRequest.getOperationId();
        List<Long> penaltiesIds = operationViolationPenaltiesRequest.getPenaltiesIds();
        Set<Long> set = new HashSet<>(penaltiesIds);
        if (set.size() < penaltiesIds.size()) {
            /* There are duplicates */
            throw new BusinessException(Constants.ErrorKeys.DUPLICATE_IN_DATA, HttpStatus.BAD_REQUEST);
        }
        Long violationId = operationViolationPenaltiesRequest.getViolationId();
        if (!violationRepository.existsById(violationId)) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Violation violation = violationRepository.getById(violationId);
//        if(!operationViolationPenaltiesRepository
//                .existsByOperationIdAndViolation(operationId,violation)){
//            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND_IN_OPERATION,
//                    HttpStatus.BAD_REQUEST);
//        }

        //add only new penalties violation
        penaltiesIds.forEach(penaltyId -> {
            if (!penaltyRepository.existsById(penaltyId)) {
                throw new BusinessException(Constants.ErrorKeys.PENALTY_NOT_FOUND, HttpStatus.BAD_REQUEST);
            }
            Penalty penalty = penaltyRepository.getById(penaltyId);
            Boolean operationViolationPenaltyExist = operationViolationPenaltiesRepository
                    .existsByOperationIdAndViolationAndPenaltyAndDeletedFalse(operationId, violation, penalty);
            if (!operationViolationPenaltyExist) {
                //save OperationViolationPenalties
                OperationViolationPenalties operationViolationPenalties =
                        OperationViolationPenalties.builder().violation(violation).penalty(penalty)
                                .operationId(operationId).build();
                operationViolationPenalties=operationViolationPenaltiesRepository.save(operationViolationPenalties);
                //save OperationViolationPenaltiesLifecycle
                OperationViolationPenaltiesLifecycle operationViolationPenaltiesLifecycle =
                        OperationViolationPenaltiesLifecycle.builder()
                                .operationViolationPenalty(operationViolationPenalties)
                                .startDate(LocalDate.now()).endDate(null)
                                .comment(null).build();
                operationViolationPenaltiesLifecycleRepository
                        .save(operationViolationPenaltiesLifecycle);
            }
        });
    }

    @Override
    public void deleteOperationViolationPenalties(UpdateOVPRequest ovpRequest) {
        Long operationId = ovpRequest.getOperationId();
        Long penaltyId = ovpRequest.getPenaltyId();
        Long violationId = ovpRequest.getViolationId();
        String comment = ovpRequest.getComment();
        if (!violationRepository.existsById(violationId)) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Violation violation = violationRepository.getById(violationId);
        if (!penaltyRepository.existsById(penaltyId)) {
            throw new BusinessException(Constants.ErrorKeys.PENALTY_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
        Penalty penalty = penaltyRepository.getById(penaltyId);
        if (!operationViolationPenaltiesRepository
                .existsByOperationIdAndViolationAndPenaltyAndDeletedFalse(operationId,
                        violation, penalty)) {
            throw new BusinessException(Constants.ErrorKeys.VIOLATION_PENALTY_NOT_FOUND_IN_OPERATION,
                    HttpStatus.BAD_REQUEST);
        }
        //soft delete in OperationViolationPenalties
        OperationViolationPenalties operationViolationPenalties =
                operationViolationPenaltiesRepository.
                        getByOperationIdAndViolationAndPenaltyAndDeletedFalse(operationId,
                                violation, penalty);
        operationViolationPenalties.setDeleted(true);
        operationViolationPenalties=operationViolationPenaltiesRepository.save(operationViolationPenalties);
        //update endDate,comment and soft delete in OperationViolationPenaltiesLifecycle
        OperationViolationPenaltiesLifecycle operationViolationPenaltiesLifecycle =
                operationViolationPenaltiesLifecycleRepository
                        .getByOperationViolationPenalty(operationViolationPenalties);
        operationViolationPenaltiesLifecycle.setEndDate(LocalDate.now());
        //TODO
//        operationViolationPenaltiesLifecycle.setDeleted(true);
        operationViolationPenaltiesLifecycle.setComment(comment);
        operationViolationPenaltiesLifecycleRepository
                .save(operationViolationPenaltiesLifecycle);
    }

    @Transactional
    @Override
    public PaginationDto getOperationViolations(OperationViolationSearchRequest
                                                        operationViolationSearchRequest) {
        int page = operationViolationSearchRequest.getPage();
        int limit = operationViolationSearchRequest.getLimit();
        String violationName = operationViolationSearchRequest.getViolationName();
        Long operationId = operationViolationSearchRequest.getOperationId();
        Pageable pageable = PageRequest.of(page, limit);
        Page<Violation> violationsPage =
                operationViolationPenaltiesRepository.getViolationOperationNotDeleted(
                        operationId,
                        violationName,
                        pageable
                );
        List<ManageViolationDto> manageViolationDtoList = new ArrayList<>();
        violationsPage.forEach(violation -> {
            double fees = violation.getOperationViolationPenalties().stream().mapToDouble(operationViolationPenalties -> operationViolationPenalties.getPenalty().getFees()).sum();
            ManageViolationDto manageViolationDto = ManageViolationDto.builder()
                    .enName(violation.getEnName())
                    .arName(violation.getArName())
                    .id(violation.getId())
                    .arDescription(violation.getArDescription())
                    .enDescription(violation.getEnDescription())
                    .fees(fees).build();

            manageViolationDtoList.add(manageViolationDto);
        });

        PaginationDto paginationDto = PaginationDto.builder()
                .content(manageViolationDtoList)
                .totalElements(violationsPage.getTotalElements())
                .totalPages(violationsPage.getTotalPages())
                .build();
        return paginationDto;

    }

    @Override
    public List<OperationViolationPenalties> getOperationViolationPenaltiesByOperationAndViolation(Long operationId, Long violationId) {
//        List<OperationViolationPenalties> penalties = operationViolationPenaltiesRepository.getOperationViolationPenaltiesByOperationAndViolation(operationId, violationId);
        List<OperationViolationPenalties> penalties = operationViolationPenaltiesRepository.getOperationViolationPenaltiesByOperationAndViolationAndDeleted(operationId, violationId, false);
        return penalties;
    }
}
