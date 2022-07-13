package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.CitationPenaltiesDTO;
import com.eden.enforcementService.common.model.entity.CitationPenalties;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OperationViolationPenaltiesMapper.class})
public interface CitationPenaltiesMapper {

    @Mappings({
            @Mapping(target = "cit_pen_Id", source = "id"),
    })
    CitationPenaltiesDTO toDto(CitationPenalties citationPenalties);
    List<CitationPenaltiesDTO> toDtoList(List<CitationPenalties> citationPenalties);
}
