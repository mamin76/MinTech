package com.eden.enforcementService.metric.clients;

import com.eden.enforcementService.config.Properties;
import com.eden.enforcementService.metric.gen.Authenticate;
import com.eden.enforcementService.metric.gen.AuthenticateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class AuthenticateClient extends WebServiceGatewaySupport {

    @Autowired
    private Properties properties;

    public AuthenticateResponse getAuth() {
        Authenticate request = new Authenticate();
        request.setUsername(properties.getMetricAPIUserName());
        request.setPassword(properties.getMetricAPIPassword());
        request.setClientGUID(properties.getMetricAPIClientGuid());
        request.setSiteCode(properties.getMetricAPISiteCode());
        AuthenticateResponse response = (AuthenticateResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request, new SoapActionCallback("http://tempuri.org/Authenticate"));
        return response;
    }
}
