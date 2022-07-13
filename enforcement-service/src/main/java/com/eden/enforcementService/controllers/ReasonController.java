package com.eden.enforcementService.controllers;

import com.eden.enforcementService.common.dto.ReasonDto;
import com.eden.enforcementService.common.model.enums.ReasonType;
import com.eden.enforcementService.common.request.ReasonRequest;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.ReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reasons")
public class ReasonController {
    @Autowired
    private ReasonService reasonService;

    @PostMapping
    public ApiResponse<List<ReasonDto>> addReasons(@Valid @RequestBody List<ReasonRequest> request) {
        List<ReasonDto> reasonDtos = reasonService.addReason(request);
        return ApiResponse.ok(reasonDtos);
    }

    @GetMapping("/{type}")
    public ApiResponse<List<ReasonDto>> getReasons(@PathVariable("type") ReasonType type) {
        List<ReasonDto> reasonDtos = reasonService.getReasons(type);
        return ApiResponse.ok(reasonDtos);
    }
}
