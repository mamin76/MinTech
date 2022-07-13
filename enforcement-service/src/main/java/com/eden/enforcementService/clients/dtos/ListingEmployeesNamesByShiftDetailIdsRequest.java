package com.eden.enforcementService.clients.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class ListingEmployeesNamesByShiftDetailIdsRequest {

    private Set<Long> shiftDetailIds;

}
