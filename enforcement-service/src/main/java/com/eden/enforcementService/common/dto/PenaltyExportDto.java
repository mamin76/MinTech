package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import lombok.Data;

@Data
public class PenaltyExportDto {
    private String enName;
    private String arName;
    private PenaltyType type;
    private double fees;
    private PenaltyMethod method;
}
