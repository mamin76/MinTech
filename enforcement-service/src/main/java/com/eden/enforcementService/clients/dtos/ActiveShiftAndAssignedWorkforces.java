package com.eden.enforcementService.clients.dtos;

import lombok.Data;


@Data
public class ActiveShiftAndAssignedWorkforces {
    private long activeShifts;
    private long assignedWorkforces;
}