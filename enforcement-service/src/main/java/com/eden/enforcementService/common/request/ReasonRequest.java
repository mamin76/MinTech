package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.ReasonType;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReasonRequest {


    @NotNull(message = Constants.ErrorKeys.EMPTY_REASON_EN)
    private String reasonEN;

    @NotNull(message = Constants.ErrorKeys.EMPTY_REASON_AR)
    private String reasonAR;

    @NotNull(message = Constants.ErrorKeys.EMPTY_REASON_TYPE)
    private ReasonType type;


}
