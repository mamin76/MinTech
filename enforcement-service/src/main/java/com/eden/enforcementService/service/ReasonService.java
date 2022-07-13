package com.eden.enforcementService.service;

import com.eden.enforcementService.common.dto.ReasonDto;
import com.eden.enforcementService.common.model.entity.Reason;
import com.eden.enforcementService.common.model.enums.ReasonType;
import com.eden.enforcementService.common.request.ReasonRequest;

import java.util.List;

public interface ReasonService {

    List<ReasonDto> addReason(List<ReasonRequest> request);

    List<ReasonDto> getReasons(ReasonType type);

    Reason getReason(Long reasonId, ReasonType aVoid);
}
