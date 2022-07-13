package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.request.PenaltyRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PenaltyMapper {
    Penalty toEntity(PenaltyRequest penaltyRequest);

    List<PenaltyDto> toDto(List<Penalty> penalty);

    PenaltyDto toDto(Penalty penalty);

}
