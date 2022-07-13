package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.VehicleType;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class  BlackListedVehicleRequest {

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_AR)
    private String plateNumberAr;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_EN)
    private String plateNumberEn;

    @NotNull(message = Constants.ErrorKeys.EMPTY_COUNTRY_ID)
    private Long countryId;

    @NotNull(message = Constants.ErrorKeys.EMPTY_COUNTRY_NAME)
    private String countryName;

    private Long colorId;

    private Long makeId;

    private String model;

    private String description;

    private String userName;

    @NotNull(message = Constants.ErrorKeys.EMPTY_VEHICLE_TYPE)
    private VehicleType type;

    private boolean deleted = false;

}
