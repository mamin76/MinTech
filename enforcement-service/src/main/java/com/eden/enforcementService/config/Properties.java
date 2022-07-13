package com.eden.enforcementService.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Properties {

    @Value("${metric.api.username}")
    private String metricAPIUserName;

    @Value("${metric.api.password}")
    private String metricAPIPassword;

    @Value("${metric.api.SiteCode}")
    private String metricAPISiteCode;

    @Value("${metric.api.ClientGUID}")
    private String metricAPIClientGuid;

    @Value("${enforcement.team.mail}")
    private String enforcementTeamMail;

    @Value("${thaki.check.token}")
    private String thakiCheckToken;
}
