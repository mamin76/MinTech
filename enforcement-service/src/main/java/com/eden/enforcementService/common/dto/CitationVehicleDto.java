package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitationVehicleDto {

    private String countryArName;
    private String countryEnName;
    private Long countryId;
    private String colorArName;
    private String colorEnName;
    private String enPlateNumber;
    private String makeArName;
    private String makeEnName;
    private String model;

}
