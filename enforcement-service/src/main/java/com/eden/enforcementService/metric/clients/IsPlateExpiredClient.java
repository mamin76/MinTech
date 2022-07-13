package com.eden.enforcementService.metric.clients;

import com.eden.enforcementService.metric.gen.Authenticate;
import com.eden.enforcementService.metric.gen.AuthenticateResponse;
import com.eden.enforcementService.metric.gen.IsPlateExpired;
import com.eden.enforcementService.metric.gen.IsPlateExpiredResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class IsPlateExpiredClient  extends WebServiceGatewaySupport {
    public IsPlateExpiredResponse getLegality(String authKey,String plateNumber) {
        IsPlateExpired request = new IsPlateExpired();
        request.setAuthenticationKey(authKey);
        request.setPlate(plateNumber);//2321JND
        request.setZoneIDList("3");
        IsPlateExpiredResponse response = (IsPlateExpiredResponse) getWebServiceTemplate()
                .marshalSendAndReceive(request,new SoapActionCallback("http://tempuri.org/IsPlateExpired"));
        return response;
    }
}
