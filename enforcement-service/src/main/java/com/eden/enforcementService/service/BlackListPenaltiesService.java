package com.eden.enforcementService.service;

import com.eden.enforcementService.common.dto.PaginationDto;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.request.BlackListPenaltyRequest;

import java.util.List;

public interface BlackListPenaltiesService {

    void addBlackListPenalties(BlackListPenaltyRequest blackListPenaltyRequest);

    void updateBlackListPenalties(BlackListPenaltyRequest blackListPenaltyRequest);

    PaginationDto getPenaltiesByBlackListId(Long blackListId);

}
