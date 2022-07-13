package com.eden.enforcementService.clients;

import com.eden.enforcementService.clients.dtos.ApiResponse;
import com.eden.enforcementService.clients.dtos.RetrievingSimpleVehicleRequest;
import com.eden.enforcementService.common.dto.CitationVehicleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "vehicle-service", path = "/vehicles")
public interface VehicleFeignClient {

    @PostMapping("/getSimpleVehicle")
    ApiResponse<List<CitationVehicleDto>> getCitationVehicleDto(RetrievingSimpleVehicleRequest retrievingSimpleVehicleRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

}