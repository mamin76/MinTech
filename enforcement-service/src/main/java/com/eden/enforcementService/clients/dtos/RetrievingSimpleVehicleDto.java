package com.eden.enforcementService.clients.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class RetrievingSimpleVehicleDto {

    private String plateNumberEn;

    private Long countryId;

}
