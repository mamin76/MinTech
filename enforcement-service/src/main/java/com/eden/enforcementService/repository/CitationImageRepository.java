package com.eden.enforcementService.repository;

import com.eden.enforcementService.common.model.entity.CitationImage;
import com.eden.enforcementService.common.response.EvidenceResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CitationImageRepository extends JpaRepository<CitationImage, Long> {

    //@Transactional
    @Query("select new com.eden.enforcementService.common.response.EvidenceResponse(ci.id, ci.content, ci.fileName, ci.contentType)  from CitationImage ci where ci.id = :id")
    EvidenceResponse getEvidenceById(@Param("id") Long id);


    @Query("select count(ci.id) from CitationImage ci where ci.id in (:ids) and ci.citation is null")
    Long countEvidenceByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query(value = "update citation_image set citation_id = :citationId where id in (:ids)", nativeQuery = true)
    void updateEvidenceWithTheNewCitationId(@Param("ids") List<Long> ids, @Param("citationId") Long citationId);

}
