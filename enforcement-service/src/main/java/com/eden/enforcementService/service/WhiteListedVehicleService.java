package com.eden.enforcementService.service;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.WhiteListDto;
import com.eden.enforcementService.common.request.WhiteListedVehicleRequest;

import java.util.List;


public interface WhiteListedVehicleService {

    WhiteListDto addWhiteListedVehicle(WhiteListedVehicleRequest request);

    boolean isCurrentlyExistWhitelistVehicle(String plateNumberEn, String country);

    PaginationDto searchPageWhiteList(String searchValue, int page, int limit);
}
