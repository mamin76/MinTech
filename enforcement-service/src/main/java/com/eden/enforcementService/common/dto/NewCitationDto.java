package com.eden.enforcementService.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCitationDto {
    private Long id;
    private String plateNumberEn;
    private String plateNumberAr;
    private String violationName;
    private Double longitude;
    private Double latitude;
    private String operationName;
    private Long violationId;
    private LocalDateTime citationDate;
    private String body;

    private List<OpenCitations> citations = new ArrayList<>();

    public String getOperationName() {
        if(Objects.isNull(operationName)){
            return "";
        }
        return operationName;
    }

    public String getBody() {
        if(Objects.isNull(body)){
            return "";
        }
        return body;
    }
}
