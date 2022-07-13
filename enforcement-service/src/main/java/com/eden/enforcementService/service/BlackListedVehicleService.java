package com.eden.enforcementService.service;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import com.eden.enforcementService.common.request.BlackListedVehicleRequest;

public interface BlackListedVehicleService {

    String addBlackListedVehicle(BlackListedVehicleRequest blackListedVehicleRequest);

    PaginationDto searchBlackListedVehicles(String keyword, int page, int size);

    String deleteBlackListedVehicle(Long id);

    BlackListedVehicle getActiveBlackListedVehicleById(Long id);

    BlackListedVehicle getBlackListedVehicleById(Long id);

    BlackListedVehicle getBlackListedVehicleByEnPlateNumberAndCountryName(String enPlateNumber,String countyName);

    String updateBlackListedVehicle(Long id, BlackListedVehicleRequest blackListedVehicleRequest);
}
