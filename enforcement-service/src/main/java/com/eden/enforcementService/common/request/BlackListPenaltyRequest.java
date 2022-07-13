package com.eden.enforcementService.common.request;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BlackListPenaltyRequest {

    @NotNull(message = Constants.ErrorKeys.PENALTY_ID_REQUIRED)
    @NotEmpty(message = Constants.ErrorKeys.PENALTY_ID_REQUIRED)
    private List<Long> penaltiesIds;

    @NotNull(message = Constants.ErrorKeys.BLACK_LIST_ID_REQUIRED)
    private Long blackListedId;

}
