package com.eden.enforcementService.common.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddingCitationResponseDto {

    private Long id;
    private String enName;
    private String arName;
    private String description;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
