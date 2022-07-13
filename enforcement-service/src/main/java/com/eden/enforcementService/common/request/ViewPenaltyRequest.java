package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import lombok.Data;

@Data
public class ViewPenaltyRequest {
    int page = 0;
    int size = 10;
    private String searchValue="";
    private PenaltyMethod methodValue;
    private PenaltyType typeValue;
}

