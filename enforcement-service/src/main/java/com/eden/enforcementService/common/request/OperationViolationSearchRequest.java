package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OperationViolationSearchRequest {
    int page= 0;
    int limit= 10;

    @NotNull(message = Constants.ErrorKeys.OPERATION_ID_REQUIRED)
    private Long operationId;

    private String violationName="";


}
