package com.eden.enforcementService.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EvidenceResponse {

    private Long id;
    private String content;
    private String fileName;
    private String contentType;

}
