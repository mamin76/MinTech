package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.request.OperationViolationPenaltiesRequest;
import com.eden.enforcementService.common.request.OperationViolationSearchRequest;
import com.eden.enforcementService.common.request.UpdateOVPRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.OperationViolationPenaltiesService;
import com.eden.enforcementService.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/OperationViolationPenalties")
public class OperationViolationPenaltiesController {

    @Autowired
    private OperationViolationPenaltiesService operationViolationPenaltiesService;

    @PostMapping
    @PreAuthorize("hasAuthority('AddOperationViolationPenalties') or hasAuthority('Super Admin') ")
    public ApiResponse addOperationViolationPenalties(@Valid @RequestBody
                                                              OperationViolationPenaltiesRequest operationViolationPenaltiesRequest) {
        operationViolationPenaltiesService.linkOperationViolationPenalties(
                operationViolationPenaltiesRequest);
        return ApiResponse.ok(Constants.SuccessMessages.SUCCESSFULLY_ADDED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('UpdateOperationViolationPenalties')")
    public ApiResponse updateOperationViolationPenalties(@Valid @RequestBody
                                                                 OperationViolationPenaltiesRequest operationViolationPenaltiesRequest) {
        operationViolationPenaltiesService.updateOperationViolationPenalties(
                operationViolationPenaltiesRequest);
        return ApiResponse.ok(Constants.SuccessMessages.SUCCESSFULLY_ADDED);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('DeleteOperationViolationPenalties')")
    public ApiResponse deleteOperationViolationPenalties(@Valid @RequestBody
                                                                 UpdateOVPRequest ovpRequest) {
        operationViolationPenaltiesService.deleteOperationViolationPenalties(ovpRequest);
        return ApiResponse.ok(Constants.SuccessMessages.SUCCESSFULLY_ADDED);
    }


    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ViewPenalty') or hasAuthority('AddCitations') or hasAuthority('ViewOperationViolationPenalties')")
    public ApiResponse getOperationViolationPenalties(OperationViolationSearchRequest
                                                              operationViolationSearchRequest) {
        System.out.println("--------------In getOperationViolationPenalties");
        log.info("--------------In getOperationViolationPenalties");
        PaginationDto paginationDto = operationViolationPenaltiesService
                .getOperationViolations(operationViolationSearchRequest);
        return ApiResponse.ok(paginationDto);
    }
}
