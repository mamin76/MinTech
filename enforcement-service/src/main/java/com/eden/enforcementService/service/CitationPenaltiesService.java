package com.eden.enforcementService.service;


import com.eden.enforcementService.common.dto.AddingCitationResponseDto;
import com.eden.enforcementService.common.dto.CitationPenaltiesProjection;
import com.eden.enforcementService.common.model.entity.CitationPenalties;
import com.eden.enforcementService.common.model.entity.Reason;

import java.util.List;
import java.util.Map;

public interface CitationPenaltiesService {

    List<CitationPenalties> buildCitationPenaltiesByOperationAndViolation(Long operationId, Long violationId, AddingCitationResponseDto addingCitationResponseDto);

    List<CitationPenalties> saveCitationPenalties(List<CitationPenalties> citationPenalties);

    List<CitationPenalties> getCitationPenalties(Long citationId);

    void voidCitationPenalties(List<CitationPenalties> openPenalties, String reason, Reason reasonObj);

    void voidCitationPenalty(CitationPenalties penalty, String reason);

    void settleCitationPenalty(CitationPenalties penalty, Long count);

    CitationPenalties findCitPenById(Long cit_pen_id);

    void updateCitationPenalty(CitationPenalties citationPenalties);

    List<CitationPenaltiesProjection> findByMonthAndOperation(String byMonth, Long operationId);

    List<CitationPenaltiesProjection> findCitationsByOperation(Long operationId);

    List<CitationPenaltiesProjection> findByMonths(List<String> months);

    Map<Long,Double> getRevenueStatisticsByYear(String year);
}
