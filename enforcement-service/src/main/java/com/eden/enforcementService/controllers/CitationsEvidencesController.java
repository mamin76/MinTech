package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.response.EvidenceResponse;
import com.eden.enforcementService.service.CitationImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.eden.enforcementService.payload.ApiResponse;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/citations/evidences")
public class CitationsEvidencesController {

    @Autowired
    private CitationImageService citationImageService;

    @PostMapping("upload")
    @PreAuthorize("hasAuthority('AddCitations')")
    public Object uploadFiles(@RequestParam("files") MultipartFile[] files) throws IOException {

        return ApiResponse.ok(citationImageService.addCitationsEvidences(files));

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ViewCitations') or hasAuthority('AddCitations')")
    public ResponseEntity<EvidenceResponse> getEvidenceById(@PathVariable Long id) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(citationImageService.getEvidenceById(id));

    }

    @GetMapping("/v2/{id}")
    @PreAuthorize("hasAuthority('ViewCitations') or hasAuthority('AddCitations')")
    public Object getEvidenceByIdV1(@PathVariable Long id) throws IOException {

        return ApiResponse.ok(citationImageService.getEvidenceById(id));

    }

}
