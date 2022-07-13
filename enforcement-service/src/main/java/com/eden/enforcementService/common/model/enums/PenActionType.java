package com.eden.enforcementService.common.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PenActionType {
    Voided("Voided"),
    Settled("Settled");

    private final String value;
}
