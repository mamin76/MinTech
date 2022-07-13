package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class DashboardSecThreeOne {
    private Map<Long, Double> revenue;
}