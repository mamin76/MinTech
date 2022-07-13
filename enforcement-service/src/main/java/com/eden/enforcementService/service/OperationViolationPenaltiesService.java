package com.eden.enforcementService.service;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.model.entity.OperationViolationPenalties;
import com.eden.enforcementService.common.request.OperationViolationPenaltiesRequest;
import com.eden.enforcementService.common.request.OperationViolationSearchRequest;
import com.eden.enforcementService.common.request.UpdateOVPRequest;

import java.util.List;

public interface OperationViolationPenaltiesService {

    List<OperationViolationPenalties> getOperationViolationPenaltiesByOperationAndViolation(Long operationId, Long violationId);

    void linkOperationViolationPenalties(OperationViolationPenaltiesRequest
                                                 operationViolationPenaltiesRequest);

    void updateOperationViolationPenalties(OperationViolationPenaltiesRequest
                                                   operationViolationPenaltiesRequest);

    void deleteOperationViolationPenalties(UpdateOVPRequest ovpRequest);


    PaginationDto getOperationViolations(OperationViolationSearchRequest
                                                 operationViolationSearchRequest);
}
