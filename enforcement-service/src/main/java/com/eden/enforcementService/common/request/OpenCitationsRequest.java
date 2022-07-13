package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OpenCitationsRequest {
    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_EN)
    private String plateNumberEn;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_LONGITUDE)
    private Double longitude;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_LATITUDE)
    private Double latitude;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_ADDRESS)
    private String address;

    private String operationName;
}
