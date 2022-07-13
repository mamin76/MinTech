package com.eden.enforcementService.service.validators.impl;

import com.eden.enforcementService.common.request.CitationAddingMultipleRequest;
import com.eden.enforcementService.common.request.CitationRequest;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.service.CitationImageService;
import com.eden.enforcementService.service.ViolationService;
import com.eden.enforcementService.service.validators.CitationValidator;
import com.eden.enforcementService.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@Primary
public class CitationValidatorImp implements CitationValidator {

    @Autowired
    private CitationImageService citationImageService;

    @Autowired
    private ViolationService violationService;

    @Override
    public void validate(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception {

        validateEvidences(citationAddingMultipleRequest);
        validateViolations(citationAddingMultipleRequest);
        validateDuplicates(citationAddingMultipleRequest);

    }

    private void validateDuplicates(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception {
        Set<CitationRequest> citationRequestSet = new HashSet<>();
        List<CitationRequest> citations = citationAddingMultipleRequest.getCitations();
        for (CitationRequest citationRequest : citations) {
            if (citationRequestSet.contains(citationRequest)) {
                throw new BusinessException(Constants.ErrorKeys.THERE_ARE_DUPLICATED_CITATIONS, HttpStatus.BAD_REQUEST);

            }
            citationRequestSet.add(citationRequest);
        }
    }

    private void validateViolations(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception {
        List<Long> violationIds = new LinkedList<>();
        citationAddingMultipleRequest.getCitations().forEach(citation ->
                violationIds.add(citation.getViolationId())
        );
        Long count = violationService.countViolationByIds(violationIds);
        if (count != violationIds.size() || violationIds.size() == 0L) {
            throw new BusinessException(Constants.ErrorKeys.NOT_VALID_VIOLATION_IDS, HttpStatus.BAD_REQUEST);

        }
    }

    private void validateEvidences(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception {
        List<Long> evidenceIds = new LinkedList<>();
        citationAddingMultipleRequest.getCitations().forEach(citation ->
                evidenceIds.addAll(citation.getEvidenceIds())
        );
        Long count = citationImageService.countEvidenceByIds(evidenceIds);
        if (count != evidenceIds.size() || evidenceIds.size() == 0L) {
            throw new BusinessException(Constants.ErrorKeys.NOT_VALID_EVIDENCE_IDS, HttpStatus.BAD_REQUEST);

        }
    }

}
