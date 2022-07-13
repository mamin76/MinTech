package com.eden.enforcementService.controllers;
import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.request.PenaltyRequest;
import com.eden.enforcementService.common.request.ViewPenaltyRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.PenaltyService;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/penalties")
//@Validated
public class PenaltiesController {

    @Autowired
    private PenaltyService penaltyService;

    @PostMapping
    @PreAuthorize("hasAuthority('AddPenalty') or hasAuthority('Super Admin')")
    public ApiResponse addPenalties(@Valid @RequestBody PenaltyRequest penaltyRequest) {
        penaltyService.addPenalties(penaltyRequest);
        return ApiResponse.ok(Constants.SuccessMessages.PENALTIES_ADDED);
    }

    @GetMapping("/existByName")
    @PreAuthorize("hasAuthority('AddPenalty') or hasAuthority('Super Admin')")
    public ApiResponse penaltyExistByName(@RequestParam String name,
                                            @RequestParam LanguageEnum lang) {
        Boolean exist = penaltyService.existByName(name,lang);
        return ApiResponse.ok(exist);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ViewPenalty')")
    public ApiResponse getPenalties() {
        List<PenaltyDto> penalties = penaltyService.getPenalties();
        return ApiResponse.ok(penalties);
    }

    @GetMapping("/details")
    @PreAuthorize("hasAuthority('ViewPenalty')")
    public ApiResponse getPenaltyDetails(@NotNull Long penaltyId) {
        PenaltyDto penalty = penaltyService.getPenaltyDetails(penaltyId);
        return ApiResponse.ok(penalty);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ViewPenalty')")
    public ApiResponse searchPagePenalties(ViewPenaltyRequest viewPenaltyRequest) {
        PaginationDto penalties = penaltyService.searchPagePenalties(viewPenaltyRequest);
        return ApiResponse.ok(penalties);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAuthority('UpdatePenalty')")
    public ApiResponse editPenalty(@Valid @RequestBody PenaltyRequest penaltyRequest) {
        penaltyService.editPenalties(penaltyRequest);
        return ApiResponse.ok(Constants.SuccessMessages.PENALTIES_ADDED);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('DeletePenalty')")
    public ApiResponse deletePenalty(@NotNull Long penaltyId) {
        penaltyService.deletePenalties(penaltyId);
        return ApiResponse.ok(Constants.SuccessMessages.PENALTIES_DELETED);
    }
}
