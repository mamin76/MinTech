package com.eden.enforcementService.common.mapper;

import com.eden.enforcementService.common.dto.WhiteListDto;
import com.eden.enforcementService.common.dto.WhiteListSearchDto;
import com.eden.enforcementService.common.model.entity.Whitelist;
import com.eden.enforcementService.common.request.WhiteListedVehicleRequest;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface WhitelistMapper {

    Whitelist toWhitelist(WhiteListedVehicleRequest request);

    List<WhiteListDto> toDtoList(List<Whitelist> whitelists);

    Set<WhiteListDto> toDtoList(Set<Whitelist> whitelists);

    List<WhiteListSearchDto> toDtoSearchList(List<Whitelist> whitelists);
    
}
