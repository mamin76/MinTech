package com.eden.enforcementService.common.request;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchCitationsRequest {

    private Long operationId;

    private List<String> citationStatuses;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate toDate;

    int page = 0;
    int limit = 10;
}
