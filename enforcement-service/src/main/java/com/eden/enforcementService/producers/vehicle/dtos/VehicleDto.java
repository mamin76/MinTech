package com.eden.enforcementService.producers.vehicle.dtos;

import com.eden.enforcementService.common.model.enums.VehicleSource;
import com.eden.enforcementService.common.model.enums.VehicleType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleDto {

    private String plateNumberAr;

    private String plateNumberEn;

    private Long countryId;

    private Long colorId;

    private Long makeId;

    private String model;

    private VehicleType type;

    private String createdBy;

    private VehicleSource source;
}
