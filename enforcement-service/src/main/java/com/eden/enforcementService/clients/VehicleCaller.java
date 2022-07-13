package com.eden.enforcementService.clients;

import com.eden.enforcementService.clients.dtos.ApiResponse;
import com.eden.enforcementService.clients.dtos.RetrievingSimpleVehicleRequest;
import com.eden.enforcementService.common.dto.CitationVehicleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class VehicleCaller {

    @Autowired
    private VehicleFeignClient vehicleFeignClient;

    public Map<String, CitationVehicleDto> getVehicle(RetrievingSimpleVehicleRequest retrievingSimpleVehicleRequest, String authorizationHeader) {

        Map<String, CitationVehicleDto> map = new LinkedHashMap<>();
        ApiResponse<List<CitationVehicleDto>> apiResponse = vehicleFeignClient.getCitationVehicleDto(retrievingSimpleVehicleRequest, authorizationHeader);
        apiResponse.getPayload().forEach(v -> {
            map.put(v.getEnPlateNumber() + "|" + v.getCountryId(), v);
        });
        return map;
    }
}
