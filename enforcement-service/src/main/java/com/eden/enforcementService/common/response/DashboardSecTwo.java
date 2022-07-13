package com.eden.enforcementService.common.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DashboardSecTwo {

    private double sumOfPaidCitations;
    private double sumOfCitations;
    private long activeShifts;
    private long assignedWorkforces;
}
