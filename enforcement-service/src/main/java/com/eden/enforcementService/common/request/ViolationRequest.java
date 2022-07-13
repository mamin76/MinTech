package com.eden.enforcementService.common.request;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ViolationRequest {

    @NotNull(message = Constants.ErrorKeys.VIOLATION_NAME_REQUIRED)
    @NotBlank(message = Constants.ErrorKeys.VIOLATION_NAME_REQUIRED)
    private String enName;

    @NotNull(message = Constants.ErrorKeys.VIOLATION_NAME_REQUIRED)
    @NotBlank(message = Constants.ErrorKeys.VIOLATION_NAME_REQUIRED)
    private String arName;

    @NotNull(message = Constants.ErrorKeys.EN_DESCRIPTION_REQUIRED)
    @NotBlank(message = Constants.ErrorKeys.EN_DESCRIPTION_REQUIRED)
    private String enDescription;

    @NotNull(message = Constants.ErrorKeys.AR_DESCRIPTION_REQUIRED)
    @NotBlank(message = Constants.ErrorKeys.AR_DESCRIPTION_REQUIRED)
    private String arDescription;

}
