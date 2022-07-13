package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ManageViolationDto {
    private Long id;
    private String enName;
    private String arName;
    private double fees;
    private String enDescription;
    private String arDescription;
}
