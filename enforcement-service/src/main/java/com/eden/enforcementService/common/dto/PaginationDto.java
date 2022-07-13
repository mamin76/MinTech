package com.eden.enforcementService.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginationDto<T>{

    private T content;
    private int totalPages;
    private Long totalElements;
}
