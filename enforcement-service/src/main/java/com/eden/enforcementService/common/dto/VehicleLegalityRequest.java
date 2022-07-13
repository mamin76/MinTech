package com.eden.enforcementService.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLegalityRequest {
    private Double latitude;
    private Double longitude;
    private String streetName;
    private Long shiftWorkForceId;
    private String operationName;
    private String countryName;
}
