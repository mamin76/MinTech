package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.model.entity.BlackListedVehicle;
import com.eden.enforcementService.common.request.BlackListedVehicleRequest;
import com.eden.enforcementService.producers.vehicle.dtos.VehicleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlackListedVehicleMapper {

    BlackListedVehicle toBlackListedVehicle(BlackListedVehicleRequest blackListedVehicleRequest);

    VehicleDto fromBlackToVehicleDto(BlackListedVehicleRequest blackListedVehicleRequest);

    BlackListedVehicle map(BlackListedVehicleRequest blackListedVehicleRequest);
}
