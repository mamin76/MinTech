package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EvidenceRequest {

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_ID)
    private  Long Id;
}
