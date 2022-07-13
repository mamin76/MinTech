package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.PenActionType;
import com.eden.enforcementService.util.Constants;
import com.eden.enforcementService.util.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CitationPenaltiesRequest {
    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_PENALTY_ID)
    private Long cit_Pen_Id;

    private Long count = 1l;

    @EnumValue(groups = PenActionType.class, enumClass = PenActionType.class, enumMethod = "")
    @NotNull(message = Constants.ErrorKeys.PENALTY_ACTION_REQUIRED)
    private String penActionType;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_VOID_REASON)
    @NotEmpty(message = Constants.ErrorKeys.EMPTY_CITATION_VOID_REASON)
    private String comment = "";

}
