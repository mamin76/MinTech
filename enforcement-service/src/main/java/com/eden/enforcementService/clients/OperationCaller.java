package com.eden.enforcementService.clients;

import com.eden.enforcementService.clients.dtos.ApiResponse;
import com.eden.enforcementService.clients.dtos.ListingEmployeesNamesByShiftDetailIdsRequest;
import com.eden.enforcementService.clients.dtos.OperationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class OperationCaller {

    @Autowired
    private OperationFeignClient operationFeignClient;

    public Map<Long, OperationDto> getAllOperations(String authorizationHeader) {
        Map<Long, OperationDto> map = new LinkedHashMap<>();
        ApiResponse<List<OperationDto>> apiResponse = operationFeignClient.getAllOperations(authorizationHeader);
        apiResponse.getPayload().forEach(operationDto -> {
            map.put(operationDto.getId(), operationDto);
        });
        return map;
    }

    public Map<Long, String> getWorkForceNamesByShiftDetailIds(String authorizationHeader, Set<Long> shiftDetailIds) {
        ListingEmployeesNamesByShiftDetailIdsRequest input = new ListingEmployeesNamesByShiftDetailIdsRequest();
        input.setShiftDetailIds(shiftDetailIds);
        ApiResponse<Map<Long, String>> apiResponse = operationFeignClient.getShiftDetailsEmployeesNamesByShiftDetailIds(authorizationHeader, input);
        return apiResponse.getPayload();
    }
}
