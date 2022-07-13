package com.eden.enforcementService.clients;

import com.eden.enforcementService.clients.dtos.ThakiCheckLegalDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "https://dc.scsc.sa", path = "/api")
public interface ThakiCheckPlateNumberFeignClient {

    @GetMapping("/IsPlateExpired")
    ThakiCheckLegalDto getThakiCheckLegalDto(@RequestHeader("token") String authorizationHeader,
                                             @RequestParam("plate_number_en") String plateNo);

}