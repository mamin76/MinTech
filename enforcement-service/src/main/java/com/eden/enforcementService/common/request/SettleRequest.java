package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SettleRequest {

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_ID)
    private List<Long> citationIds;
}
