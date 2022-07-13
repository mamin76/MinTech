package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.PenalityStatus;
import lombok.Data;

@Data
public class CitationPenaltiesExportDTO {
    private Long cit_pen_Id;
    private OperationViolationPenaltiesExportDTO operationViolationPenalty;
    private PenalityStatus status;
}
