package com.eden.enforcementService.clients.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class InvoiceDto {
    private Long invoiceNumber;
    private ZonedDateTime invoiceDate;
    private BigDecimal totalAmount;
    private BigDecimal totalVATAmount;
    private BigDecimal vat;
    private BigDecimal totalAmountWithVAT;
    private String paymentMethodId;
    private String comment;
    private String source;
    private List<InvoiceItem> items;
    private boolean voided;
    private String voidedMsg;
    private BigDecimal totalDiscount;
    private String qrData;
    private String description;

}
