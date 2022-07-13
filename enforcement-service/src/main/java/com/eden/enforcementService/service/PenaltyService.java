package com.eden.enforcementService.service;
import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.model.enums.LanguageEnum;
import com.eden.enforcementService.common.request.PenaltyRequest;
import com.eden.enforcementService.common.request.ViewPenaltyRequest;


import java.util.Collection;
import java.util.List;

public interface PenaltyService {
    void addPenalties(PenaltyRequest penaltyRequest);

    void editPenalties(PenaltyRequest penaltyRequest);

    List<PenaltyDto> getPenalties();

    PaginationDto searchPagePenalties(ViewPenaltyRequest viewPenaltyRequest);

    void deletePenalties(Long penaltyId);

    PenaltyDto getPenaltyDetails(Long penaltyId);
    Integer countPenaltiesByIds(Collection<Long> ids);

    PenaltyDto getPenaltyByEnName(String enName);

    Boolean existByName(String name, LanguageEnum lang);
}
