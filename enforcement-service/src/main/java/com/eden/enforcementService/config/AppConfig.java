package com.eden.enforcementService.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
@Configuration
public class AppConfig {

    @PostConstruct
    public void init() {
        String timeZone = "Asia/Riyadh";
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
        log.info("App running in [" + timeZone + "] timeZone;, current time is " + new Date().toString());
    }
}
