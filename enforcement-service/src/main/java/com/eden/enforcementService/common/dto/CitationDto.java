package com.eden.enforcementService.common.dto;


import com.eden.enforcementService.common.model.enums.CitationStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class CitationDto {

    private Long id;
    private String plateNumberEn;
    private String violationName;
    private String violationNameAr;
    private Long violationId;
    private List<CitationPenaltiesDTO> citationPenalties;
    private List<Long> imagesIds = new ArrayList<>();
    private LocalDateTime createdDate;
    private double amount;
    private String streetName;
    private String comment;
    private String createdBy;
    private Long operationId;
    private String operationName;
    private CitationVehicleDto citationVehicleDto;
    private Long countryId;
    @Enumerated(EnumType.STRING)
    private CitationStatus status;


}
