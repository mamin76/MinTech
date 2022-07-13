package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.VehicleType;
import lombok.Data;

import java.util.List;

@Data
public class WhiteListSearchDto {
    private Long id;

    private String plateNumberAr;

    private String plateNumberEn;

    private String country;

    private Long colorId;

    private Long makeId;

    private String model;

    private VehicleType type;

    private Long operationId;

    private List<WhiteListDetailsDto> whitelistDetails;
}
