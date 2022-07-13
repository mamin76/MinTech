package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OpenCitationPenaltiesDto {

    private String enName;
    private String arName;
    private double fees;
}
