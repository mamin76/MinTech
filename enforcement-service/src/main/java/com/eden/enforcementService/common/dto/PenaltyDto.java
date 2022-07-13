package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import lombok.Data;

@Data
public class PenaltyDto {

    private Long id;
    private String enName;
    private String arName;
    private PenaltyType type;
    private double fees;
    private String email;
    private String subject;
    private String body;
    private PenaltyMethod method;
    private String enDescription;
    private String arDescription;

}
