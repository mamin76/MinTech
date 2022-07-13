package com.eden.enforcementService.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSecThree {

    private double openCitations;
    private double paidCitations;
    private double voidedCitations;

}
