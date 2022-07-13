package com.eden.enforcementService.producers.citationkpi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CitationKpi {
    private Integer citationCount;
    private Long shiftWorkForceId;
}
