package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import com.eden.enforcementService.common.request.BlackListedVehicleRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.BlackListedVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/blackListedVehicles")
public class BlackListedVehicleController {

    @Autowired
    private BlackListedVehicleService blackListedVehicleService;

    @PostMapping
    @PreAuthorize("hasAuthority('AddBlacklist')")
    public ApiResponse<String> addBlackListedVehicle(@Valid @RequestBody BlackListedVehicleRequest blackListedVehicleRequest) throws Exception {
        return ApiResponse.ok(blackListedVehicleService.addBlackListedVehicle(blackListedVehicleRequest));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ViewBlacklist')")
    public ApiResponse<PaginationDto> getBlackListedVehicles(@RequestParam(value = "keyword", required = false) String keyword,
                                                             @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size", defaultValue = "10") Integer size) throws Exception {
        return ApiResponse.ok(blackListedVehicleService.searchBlackListedVehicles(keyword, page, size));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ViewBlacklist')")
    public ApiResponse<BlackListedVehicle> getBlackListedVehiclesById(@PathVariable("id") Long id) throws Exception {
        return ApiResponse.ok(blackListedVehicleService.getActiveBlackListedVehicleById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DisableBlacklist')")
    public ApiResponse<String> deleteBlackListedVehicle(@PathVariable("id") Long id) throws Exception {
        return ApiResponse.ok(blackListedVehicleService.deleteBlackListedVehicle(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('UpdateBlacklist')")
    public ApiResponse<String> updateBlackListedVehicle(@PathVariable("id") Long id,
                                                        @Valid @RequestBody BlackListedVehicleRequest blackListedVehicleRequest) throws Exception {
        return ApiResponse.ok(blackListedVehicleService.updateBlackListedVehicle(id,blackListedVehicleRequest));
    }
}
