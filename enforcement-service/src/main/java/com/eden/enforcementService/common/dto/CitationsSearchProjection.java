package com.eden.enforcementService.common.dto;

import java.time.LocalDateTime;

public interface CitationsSearchProjection {
    LocalDateTime getCitationDate();
    Long getCitationId();
    LocalDateTime getPaymentDateTime();
    String getPlateNumberAr();
    String getPlateNumberEn();
    String getRemarks();
    Long getShiftWorkForceId();
    String getStatus();
    String getStreetName();
    Double getTotalFees();
    Long getViolationId();
    String getViolationNameAr();
    String getViolationNameEn();
}
