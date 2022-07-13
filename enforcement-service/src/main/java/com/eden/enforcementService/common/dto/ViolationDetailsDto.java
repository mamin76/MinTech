package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.entity.Penalty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ViolationDetailsDto {

    private Long id;

    private String enName;
    private String arName;

    private List<PenaltyDto> penaltyList;
    private double violationFees;

    private String enDescription;
    private String arDescription;

}
