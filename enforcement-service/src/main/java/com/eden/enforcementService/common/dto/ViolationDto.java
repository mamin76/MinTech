package com.eden.enforcementService.common.dto;

import lombok.Data;

@Data
public class ViolationDto {

    private Long id;
    private String enName;
    private String arName;
    private String enDescription;
    private String arDescription;
}
