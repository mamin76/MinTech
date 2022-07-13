package com.eden.enforcementService.common.request;

import com.eden.enforcementService.common.model.enums.CitationStatus;
import com.eden.enforcementService.common.model.enums.Sorting;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ViewCitationsRequest {
    int offset = 0;
    int limit = 10;

    private String query;

    private List<CitationStatus> statusList;
    private Set<String> sortBy = new HashSet<>();

    // @EnumValue(enumClass = Sort.Direction.class, message = "Invalid Sort Direction", enumMethod = "isValidName")
    private String sortDirection = Sorting.DESC.toString();

}
