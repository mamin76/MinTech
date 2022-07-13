package com.eden.enforcementService.common.mapper;
import com.eden.enforcementService.common.dto.PenaltyDto;
import com.eden.enforcementService.common.dto.ViolationDto;
import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.model.entity.Violation;
import com.eden.enforcementService.common.request.ViolationRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ViolationMapper {
    Violation toEntity(ViolationRequest violationRequest);

    List<ViolationDto> toDto(List<Violation> violations);

    ViolationDto toDto(Violation violations);

}
