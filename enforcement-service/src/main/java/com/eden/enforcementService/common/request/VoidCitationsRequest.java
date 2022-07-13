package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VoidCitationsRequest {
    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_ID)
    private Long id;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_VOID_REASON)
    private String reason;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_VOID_REASON_ID)
    private Long reasonId;
}
