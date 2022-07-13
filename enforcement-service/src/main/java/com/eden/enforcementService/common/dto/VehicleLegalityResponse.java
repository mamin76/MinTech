package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLegalityResponse {
    private Boolean legal;
    private VehicleStatus vehicleStatus;
}
