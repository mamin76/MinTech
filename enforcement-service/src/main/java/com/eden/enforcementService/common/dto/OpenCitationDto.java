package com.eden.enforcementService.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenCitationDto {
    private String address;
    private String plateNumberEn;
    private String plateNumberAr;
    List<OpenCitations> citations = new ArrayList<>();
    private Double longitude;
    private Double latitude;
    private String operationName;
}
