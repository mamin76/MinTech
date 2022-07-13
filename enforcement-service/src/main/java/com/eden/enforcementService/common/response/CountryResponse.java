package com.eden.enforcementService.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class CountryResponse {

    private Long id;
    private String enName;
    private String arName;

}
