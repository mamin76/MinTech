package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.ReasonDto;
import com.eden.enforcementService.common.model.entity.Reason;
import com.eden.enforcementService.common.request.ReasonRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ReasonMapper {

    Reason toReason(ReasonRequest citationRequest);

    List<ReasonDto> toDtoList(List<Reason> citations);

    Set<ReasonDto> toDtoList(Set<Reason> citations);

}
