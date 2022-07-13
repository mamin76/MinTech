package com.eden.enforcementService.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackListMailDto {
    private Long id;
    private String plateNumberEn;
    private Double longitude;
    private Double latitude;
    private String operationName;
    private String body;
    private String countryName;
    private String streetName;
}
