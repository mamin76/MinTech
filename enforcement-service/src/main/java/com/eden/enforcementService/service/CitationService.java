package com.eden.enforcementService.service;

import com.eden.enforcementService.clients.dtos.InvoiceDto;
import com.eden.enforcementService.common.dto.*;
import com.eden.enforcementService.common.request.*;
import com.eden.enforcementService.common.response.DashboardSecThree;
import com.eden.enforcementService.common.response.DashboardSecTwo;

import java.util.List;

public interface CitationService {

    List<AddingCitationResponseDto> addCitations(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception;

    PaginationDto getUnpaidCitations(ViewCitationsRequest request);

    void voidCitation(VoidCitationsRequest request);

    List<CitationDto> citationDetails(citationsDetailsRequest request);

    InvoiceDto handleCitations(String authorizationHeader, HandelCitationRequestList request);

    List<CitationDto> fetchAll(List<Long> citationsIds);

    DashboardSecOne dashboardSecOne(DashSecOneRequest request);

    DashboardSecTwo dashboardSecTwo(DashSecTwoRequest request);

    DashboardSecThree dashboardSecThree(DashSecThreeRequest request);
    boolean checkVehicleLegality(String enPlateNumber, Double latitude, Double longitude, String streetName, Long shiftWorkForceId, String operationName);

    OpenCitationDto getOpenCitations(OpenCitationsRequest request);

    void sendMailOpenCitations(OpenCitationsRequest request);

    DashboardSecThreeOne dashboardSecThreeOne(DashSecThreeOneRequest request);

    VehicleLegalityResponse isLegalParking(String enPlateNumber, VehicleLegalityRequest vehicleLegalityRequest);

    InvoiceDto settleCitations(String authorizationHeader, List<Long> citationIds);


    PaginationDto listCitationForMobile(ListCitationsForMobileRequest request);

    PaginationDto searchCitation(SearchCitationsRequest request);
}
