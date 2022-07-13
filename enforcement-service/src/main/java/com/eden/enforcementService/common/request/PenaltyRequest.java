package com.eden.enforcementService.common.request;
import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import com.eden.enforcementService.util.Constants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class PenaltyRequest {

    @NotNull(message = Constants.ErrorKeys.PENALTY_NAME_REQUIRED)
    private String enName;

    @NotNull(message = Constants.ErrorKeys.PENALTY_NAME_REQUIRED)
    private String arName;

    private PenaltyType type;

    @NotNull(message = Constants.ErrorKeys.PENALTY_METHOD_REQUIRED)
    private PenaltyMethod method;

    @NotNull(message = Constants.ErrorKeys.PENALTY_VALUE_REQUIRED)
    private double fees;

    @Email(message = Constants.ErrorKeys.PENALTY_TO_EMAIL_NOT_VALID, regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    private String email;

    private String subject;

    private String body;

    @NotNull(message = Constants.ErrorKeys.EN_DESCRIPTION_REQUIRED)
    private String enDescription;

    @NotNull(message = Constants.ErrorKeys.AR_DESCRIPTION_REQUIRED)
    private String arDescription;

    private String id;

}
