package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ListCitationsForMobileRequest {

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER)
    private String plateNumber;

    int page = 1;
    int limit = 10;
}
