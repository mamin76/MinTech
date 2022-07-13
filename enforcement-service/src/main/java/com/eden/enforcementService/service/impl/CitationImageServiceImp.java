package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.common.model.entity.CitationImage;
import com.eden.enforcementService.common.response.EvidenceResponse;
import com.eden.enforcementService.repository.CitationImageRepository;
import com.eden.enforcementService.service.CitationImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

@Service
@Primary
public class CitationImageServiceImp implements CitationImageService {

    @Autowired
    private CitationImageRepository citationImageRepository;


    @Override
    @Transactional
    public List<Long> addCitationsEvidences(MultipartFile[] files) throws IOException {
        List<Long> imageIds = new LinkedList<>();
        String encodedString;
        for (MultipartFile file : files) {
            CitationImage citationImage = new CitationImage();

            encodedString = Base64.getEncoder().encodeToString(file.getBytes());

            citationImage.setContent(encodedString);
            citationImage.setFileName(file.getOriginalFilename());
            citationImage.setContentType(file.getContentType());
            citationImage = citationImageRepository.save(citationImage);
            citationImage.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
            imageIds.add(citationImage.getId());
        }

        return imageIds;
    }
    @Transactional
    @Override
    public EvidenceResponse getEvidenceById(Long id) {
        return citationImageRepository.getEvidenceById(id);
    }

    @Override
    public void updateEvidenceWithTheNewCitationId(List<Long> evidenceIds, Long citationId){

        citationImageRepository.updateEvidenceWithTheNewCitationId(evidenceIds, citationId);
    }

    @Override
    public Long countEvidenceByIds(List<Long> ids) {
        return citationImageRepository.countEvidenceByIds(ids);
    }
}
