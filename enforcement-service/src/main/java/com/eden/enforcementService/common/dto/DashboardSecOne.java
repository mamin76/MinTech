package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class DashboardSecOne {
    private double sumOfPaidCitations;
    private double sumOfCitations;
}
