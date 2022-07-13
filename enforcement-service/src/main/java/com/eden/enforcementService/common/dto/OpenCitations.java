package com.eden.enforcementService.common.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OpenCitations {
    private Long id;
    private String violationName;
    private String violationNameAr;
    private Long violationId;
    private List<OpenCitationPenaltiesDto> citationPenalties;
    private LocalDateTime createdDate;

}
