package com.eden.enforcementService.common.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExportCitationsReq {
    private List<Long> citationsIds = new ArrayList<>();
}
