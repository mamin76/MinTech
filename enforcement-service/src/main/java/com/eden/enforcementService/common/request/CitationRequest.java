package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.CitationStatus;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Data
public class CitationRequest {

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_AR)
    private String plateNumberAr;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PLATE_NUMBER_EN)
    private String plateNumberEn;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_COUNTRY_ID)
    private Long countryId;

    @NotNull(message = Constants.ErrorKeys.EMPTY_SHIFT_WORK_FORCE_ID)
    private Long shiftWorkforceId;

    private CitationStatus status = CitationStatus.Opened;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_LONGITUDE)
    private Double longitude;

    @NotNull(message = Constants.ErrorKeys.EMPTY_CITATION_LATITUDE)
    private Double latitude;

    @NotNull(message = Constants.ErrorKeys.EMPTY_VIOLATION_ID)
    private Long violationId;

    @NotEmpty(message = Constants.ErrorKeys.EMPTY_CITATIONS_EVIDENCE_REQUEST)
    private List<Long> evidenceIds;

    private String createdBy;

    private String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CitationRequest that = (CitationRequest) o;
        return plateNumberAr.equals(that.plateNumberAr) && plateNumberEn.equals(that.plateNumberEn) && countryId.equals(that.countryId) && violationId.equals(that.violationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plateNumberAr, plateNumberEn, countryId, violationId);
    }
}
