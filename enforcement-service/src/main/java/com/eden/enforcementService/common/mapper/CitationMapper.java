package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.CitationDto;
import com.eden.enforcementService.common.dto.CitationsSearchDto;
import com.eden.enforcementService.common.dto.CitationsSearchProjection;
import com.eden.enforcementService.common.model.entity.Citation;
import com.eden.enforcementService.common.request.CitationRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CitationPenaltiesMapper.class})
public interface CitationMapper {

    List<Citation> toEntityList(List<CitationRequest> citationRequests);

    Citation toCitation(CitationRequest citationRequest);

    List<CitationDto> toDtoList(List<Citation> citations);

    Set<CitationDto> toDtoList(Set<Citation> citations);

    CitationsSearchDto toCitationsSearchDto(CitationsSearchProjection citationsSearchProjection);

    List<CitationsSearchDto> toCitationsSearchDto(List<CitationsSearchProjection> citationsSearchProjections);


}
