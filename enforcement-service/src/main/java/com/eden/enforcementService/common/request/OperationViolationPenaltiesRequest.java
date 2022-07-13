package com.eden.enforcementService.common.request;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
public class OperationViolationPenaltiesRequest {

    @NotNull(message = Constants.ErrorKeys.PENALTY_ID_REQUIRED)
    private List<Long> penaltiesIds;

    @NotNull(message = Constants.ErrorKeys.VIOLATION_ID_REQUIRED)
    private Long violationId;

    @NotNull(message = Constants.ErrorKeys.OPERATION_ID_REQUIRED)
    private Long operationId;

    private List<LocalDate> startDates;

    private List<LocalDate> endDates;



}
