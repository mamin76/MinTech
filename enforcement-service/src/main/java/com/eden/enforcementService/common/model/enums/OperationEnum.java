package com.eden.enforcementService.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationEnum {
    Dammam("Dammam"),
    Khobbar("Al Khobar"),
    Burayddah("Buraydah"),
    All("All");

    private final String value;
}
