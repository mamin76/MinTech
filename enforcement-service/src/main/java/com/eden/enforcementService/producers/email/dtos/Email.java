package com.eden.enforcementService.producers.email.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String subject;
    private String toEmail;
    private String body;
    private String ccEmail;
    private boolean htmlFormat;

}
