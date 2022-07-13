package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.WhiteListDto;
import com.eden.enforcementService.common.request.WhiteListedVehicleRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.WhiteListedVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/whiteListedVehicles")
public class WhiteListController {

    @Autowired
    private WhiteListedVehicleService whiteListedVehicleService;

    @PostMapping
//    @PreAuthorize("hasAuthority('AddBlacklist')")
    public ApiResponse<String> addBlackListedVehicle(@Valid @RequestBody WhiteListedVehicleRequest request) {
        WhiteListDto whiteListDto = whiteListedVehicleService.addWhiteListedVehicle(request);
        return ApiResponse.ok("");
    }

    @GetMapping
    public ApiResponse<PaginationDto> getWhiteList(@Valid @RequestParam(defaultValue ="") String searchValue,
                                                        @RequestParam(defaultValue ="0") int page,
                                                        @RequestParam(defaultValue ="10") int limit) {
        PaginationDto whiteListDtoPage = whiteListedVehicleService
                .searchPageWhiteList(searchValue,page,limit);
        return ApiResponse.ok(whiteListDtoPage);
    }


}
