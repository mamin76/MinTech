package com.eden.enforcementService.service.validators;

import com.eden.enforcementService.common.request.CitationAddingMultipleRequest;

public interface CitationValidator {
    void validate(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception;
}
