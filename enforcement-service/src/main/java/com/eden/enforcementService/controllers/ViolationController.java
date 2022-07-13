package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.ViolationDetailsDto;
import com.eden.enforcementService.common.dto.ViolationDto;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.request.ViewViolationsRequest;
import com.eden.enforcementService.common.request.ViolationRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.ViolationService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/violation")
public class ViolationController {

    @Autowired
    private ViolationService violationService;

    @PostMapping
    @PreAuthorize("hasAuthority('AddViolations') or hasAuthority('Super Admin')")
    public ApiResponse addViolation(@Valid @RequestBody ViolationRequest violationRequest) {
        violationService.addViolation(violationRequest);
        return ApiResponse.ok(Constants.SuccessMessages.VIOLATION_ADDED);
    }

    @GetMapping("/existByName")
    @PreAuthorize("hasAuthority('AddViolations') or hasAuthority('Super Admin')")
    public ApiResponse violationExistByName(@RequestParam String name,
                                            @RequestParam LanguageEnum lang) {
        Boolean exist = violationService.existByName(name,lang);
        return ApiResponse.ok(exist);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ViewViolations')")
    public ApiResponse getViolations() {
        List<ViolationDto> violations = violationService.getViolations();
        return ApiResponse.ok(violations);
    }

    @GetMapping("/details")
    @PreAuthorize("hasAuthority('ViewViolations')")
    public ApiResponse getViolationDetails(@RequestParam Long violationId
            , @RequestParam Long operationId) {
        ViolationDetailsDto violationDetails = violationService.getViolationDetails(violationId, operationId);
        return ApiResponse.ok(violationDetails);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ViewViolations')")
    public ApiResponse searchViolations(ViewViolationsRequest viewViolationsRequest) {
        PaginationDto paginationDto = violationService.searchViolations(viewViolationsRequest);
        return ApiResponse.ok(paginationDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DisableViolations')")
    public ApiResponse<String> disableViolation(@PathVariable("id") Long id) {
        return ApiResponse.ok(violationService.deleteViolation(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ViewViolations')")
    public ApiResponse<ViolationDto> getViolationById(@PathVariable("id") Long id) {
        return ApiResponse.ok(violationService.getViolationsById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UpdateViolations')")
    public ApiResponse<String> updateBlackListedVehicle(@PathVariable("id") Long id,
                                                        @Valid @RequestBody ViolationRequest violationRequest) throws Exception {
        return ApiResponse.ok(violationService.updateViolation(id, violationRequest));
    }


}
