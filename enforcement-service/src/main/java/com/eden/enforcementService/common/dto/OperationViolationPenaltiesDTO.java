package com.eden.enforcementService.common.dto;

import lombok.Data;

@Data
public class OperationViolationPenaltiesDTO {

    private Long op_V_P_Id;

    private PenaltyDto penalty;

    private ViolationDto violation;

    private Long operationId;
}
