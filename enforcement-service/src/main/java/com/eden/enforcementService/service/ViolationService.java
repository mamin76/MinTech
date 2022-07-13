package com.eden.enforcementService.service;


import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.ViolationDetailsDto;
import com.eden.enforcementService.common.dto.ViolationDto;
import com.eden.enforcementService.common.model.entity.Violation;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.request.ViewViolationsRequest;
import com.eden.enforcementService.common.request.ViolationRequest;

import java.util.List;

public interface ViolationService {
    void addViolation(ViolationRequest violationRequest);

    List<ViolationDto> getViolations();

    ViolationDetailsDto getViolationDetails(Long id, Long operationId);

    Long countViolationByIds(List<Long> violationIds);

    PaginationDto searchViolations(ViewViolationsRequest viewViolationsRequest);

    String deleteViolation(Long id);

    public Violation getViolationById(Long id);

    ViolationDto getViolationsById(Long id);

    public String updateViolation(Long id, ViolationRequest violationRequest);

    Boolean existByName(String name, LanguageEnum lang);
}
