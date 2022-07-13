package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateOVPRequest {


    @NotNull(message = Constants.ErrorKeys.PENALTY_ID_REQUIRED)
    private Long penaltyId;

    @NotNull(message = Constants.ErrorKeys.VIOLATION_ID_REQUIRED)
    private Long violationId;

    @NotNull(message = Constants.ErrorKeys.OPERATION_ID_REQUIRED)
    private Long operationId;

    private String comment;
}
