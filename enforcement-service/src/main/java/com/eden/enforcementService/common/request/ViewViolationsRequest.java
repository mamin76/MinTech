package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.Sorting;
import lombok.Data;


@Data
public class ViewViolationsRequest {
    int page = 1;
    int size = 10;

    private String query;

    private String sortBy = "id";

    private String sortDirection = Sorting.DESC.toString();

}
