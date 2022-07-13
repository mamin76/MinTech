package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class InvoiceRequest {
    @Valid
    @NotEmpty(message = Constants.ErrorKeys.EMPTY_ITEMS)
    private List<InvoiceItemRequest> items;

    @NotNull(message = Constants.ErrorKeys.EMPTY_PAYMENT_METHOD)
    private String paymentMethod;

    @NotNull(message = Constants.ErrorKeys.EMPTY_SOURCE)
    private String source;

    private String comment;
    private String description;

}
