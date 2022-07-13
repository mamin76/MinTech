package com.eden.enforcementService.common.dto;

import lombok.Data;

@Data
public class OperationViolationPenaltiesExportDTO {
    private PenaltyExportDto penalty;

    private ViolationExportDto violation;
}
