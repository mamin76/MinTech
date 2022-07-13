package com.eden.enforcementService.common.request;

import com.eden.enforcementService.util.Constants;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
public class InvoiceItemRequest {
    @NotNull(message = Constants.ErrorKeys.EMPTY_ITEM_NAME)
    private String itemName;

    @NotNull(message = Constants.ErrorKeys.EMPTY_AMOUNT)
    @Positive(message = Constants.ErrorKeys.POSITIVE_ITEM_AMOUNT)
    private BigDecimal amount;

    private Integer quantity;

    public Integer getQuantity() {
        return quantity == null || quantity < 0 ? 1 : quantity;
    }

}
