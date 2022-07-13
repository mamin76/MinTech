package com.eden.enforcementService.producers.scankpi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanKpi {
    private Integer scanCount;
    private Long shiftWorkForceId;
}
