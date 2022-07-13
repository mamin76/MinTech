package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.request.BlackListPenaltyRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.BlackListPenaltiesService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/blackListPenalties")
public class BlackListPenaltiesController {

    @Autowired
    private BlackListPenaltiesService blackListPenaltiesService;

    @PostMapping
    @PreAuthorize("hasAuthority('AddBlacklist')")
    public ApiResponse addBlackListPenalties(@Valid @RequestBody
                                                     BlackListPenaltyRequest blackListPenaltyRequest) {
        blackListPenaltiesService.addBlackListPenalties(blackListPenaltyRequest);
        return ApiResponse.ok(Constants.SuccessMessages.SUCCESSFULLY_ADDED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('AddBlacklist')")
    public ApiResponse updateBlackListPenalties(@Valid @RequestBody
                                                        BlackListPenaltyRequest blackListPenaltyRequest) {
        blackListPenaltiesService.updateBlackListPenalties(blackListPenaltyRequest);
        return ApiResponse.ok(Constants.SuccessMessages.SUCCESSFULLY_ADDED);
    }

    @GetMapping("/penalties")
    @PreAuthorize("hasAuthority('ViewBlacklist')")
    public ApiResponse<PaginationDto> getPenaltiesByBlackListVehicleId(@RequestParam(required = true, defaultValue = "0") Long blackListId) {

        return ApiResponse.ok(blackListPenaltiesService.getPenaltiesByBlackListId(blackListId));
    }

}
