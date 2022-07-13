package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.VehicleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class WhiteListDto {
    private Long id;

    private String plateNumberAr;

    private String plateNumberEn;

    private String country;

    private Long colorId;

    private Long makeId;

    private String model;

    private VehicleType type;

    private Long operationID;

//    private List<WhitelistDetails> whitelistDetails = new ArrayList<>();

    private LocalDate fromDate;

    private LocalDate toDate;

    private LocalTime timeFrom;

    private LocalTime timeTo;

    private String userName;

    private String description;
}
