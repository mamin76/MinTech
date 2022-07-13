package com.eden.enforcementService.common.dto;

import com.eden.enforcementService.common.model.enums.ReasonType;
import lombok.Data;

@Data
public class ReasonDto {

    private Long id;
    private String reasonEN;
    private String reasonAR;
    private ReasonType type;

}
