package com.eden.enforcementService.metric;

import com.eden.enforcementService.metric.clients.AuthenticateClient;
import com.eden.enforcementService.metric.clients.IsPlateExpiredClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class MetricConfig {
    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.eden.enforcementService.metric.gen");
        return marshaller;
    }
    @Bean
    public AuthenticateClient authenticateClient(Jaxb2Marshaller marshaller) {
        AuthenticateClient client = new AuthenticateClient();
        client.setDefaultUri("http://idex.mi-office.com:42010/mi-xchange/service/soap/v2018.2/outgoing.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    } @Bean
    public IsPlateExpiredClient isPlateExpiredClient(Jaxb2Marshaller marshaller) {
        IsPlateExpiredClient client = new IsPlateExpiredClient();
        client.setDefaultUri("http://idex.mi-office.com:42010/mi-xchange/service/soap/v2018.2/outgoing.asmx");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }
}
