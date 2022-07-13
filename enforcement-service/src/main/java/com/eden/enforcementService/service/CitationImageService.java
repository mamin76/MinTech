package com.eden.enforcementService.service;

import com.eden.enforcementService.common.response.EvidenceResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CitationImageService {

    List<Long> addCitationsEvidences(MultipartFile[] files) throws IOException;

    EvidenceResponse getEvidenceById(Long id);

    void updateEvidenceWithTheNewCitationId(List<Long> evidenceIds, Long citationId);

    Long countEvidenceByIds(List<Long> ids);
}
