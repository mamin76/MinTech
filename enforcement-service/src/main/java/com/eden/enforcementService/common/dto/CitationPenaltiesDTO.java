package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.PenalityStatus;
import lombok.Data;

@Data
public class CitationPenaltiesDTO {

    private Long cit_pen_Id;
    private OperationViolationPenaltiesDTO operationViolationPenalty;
    private PenalityStatus status;


}
