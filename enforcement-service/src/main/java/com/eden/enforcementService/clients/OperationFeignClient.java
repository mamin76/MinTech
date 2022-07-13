package com.eden.enforcementService.clients;

import com.eden.enforcementService.clients.dtos.ActiveShiftAndAssignedWorkforces;
import com.eden.enforcementService.clients.dtos.ApiResponse;
import com.eden.enforcementService.clients.dtos.ListingEmployeesNamesByShiftDetailIdsRequest;
import com.eden.enforcementService.clients.dtos.OperationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@FeignClient(value = "operation-service", path = "/operation")
public interface OperationFeignClient {

    @GetMapping("/shift/countShiftAndWorkforces")
    ApiResponse<ActiveShiftAndAssignedWorkforces> getCountShiftsAndWorkforces();

    @GetMapping("/operation/all/custom")
    ApiResponse<List<OperationDto>> getAllOperations(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader);

    @PostMapping("/shift/getShiftDetailsEmployeesNamesByShiftDetailIds")
    ApiResponse<Map<Long, String>> getShiftDetailsEmployeesNamesByShiftDetailIds(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody ListingEmployeesNamesByShiftDetailIdsRequest listingEmployeesNamesByShiftDetailIdsRequest);

}