package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.WhiteListDetailsDto;
import com.eden.enforcementService.common.dto.WhiteListDto;
import com.eden.enforcementService.common.model.entity.Whitelist;
import com.eden.enforcementService.common.model.entity.WhitelistDetails;
import com.eden.enforcementService.common.request.WhiteListedDetailsRequest;
import com.eden.enforcementService.common.request.WhiteListedVehicleRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface WhitelistDetailsMapper {

    WhitelistDetails toWhitelistDetails(WhiteListedDetailsRequest request);

    List<WhiteListDetailsDto> toDtoList(List<WhitelistDetails> whitelistDetails);

    Set<WhiteListDetailsDto> toDtoList(Set<WhitelistDetails> whitelistDetails);

}
