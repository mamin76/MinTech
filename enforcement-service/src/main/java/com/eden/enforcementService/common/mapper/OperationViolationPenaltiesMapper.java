package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.OperationViolationPenaltiesDTO;
import com.eden.enforcementService.common.model.entity.OperationViolationPenalties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PenaltyMapper.class, ViolationMapper.class})
public interface OperationViolationPenaltiesMapper {

    @Mappings({
            @Mapping(target = "op_V_P_Id", source = "id"),
    })
    OperationViolationPenaltiesDTO toDto(OperationViolationPenalties shift);

    List<OperationViolationPenaltiesDTO> toDtoList(List<OperationViolationPenalties> citationPenalties);
}
