package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class WhiteListDetailsDto {

    private String userName;

    private String countryName;

    private LocalDate fromDate;

    private LocalDate toDate;

    private LocalTime timeFrom;

    private LocalTime timeTo;

    private String description;

}
