package com.eden.enforcementService.clients.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceItem {
    private String itemName;
    private BigDecimal amount;
    private BigDecimal totalAmount;
    private Integer quantity;
}
