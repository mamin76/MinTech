package com.eden.enforcementService.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CitationsSearchDto {
    private LocalDateTime citationDate;
    private Long citationId;
    private LocalDateTime paymentDateTime;
    private String plateNumberAr;
    private String plateNumberEn;
    private String remarks;
    private Long shiftWorkForceId;
    private String status;
    private String streetName;
    private Double totalFees;
    private Long violationId;
    private String violationNameAr;
    private String violationNameEn;
    private String workForceName;
}
