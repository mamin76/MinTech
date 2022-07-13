package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CitationAddingMultipleRequest {

    @Valid
    @NotEmpty(message = Constants.ErrorKeys.EMPTY_CITATIONS_REQUEST)
    private List<CitationRequest> citations;

    @Valid
    @NotNull(message = Constants.ErrorKeys.EMPTY_OPERATION_ID)
    private Long operationId;

    private String streetName;

    private String operationName;

}
