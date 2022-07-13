package com.eden.enforcementService.controllers;

import com.eden.enforcementService.clients.dtos.InvoiceDto;
import com.eden.enforcementService.common.dto.*;
import com.eden.enforcementService.common.request.*;
import com.eden.enforcementService.common.response.DashboardSecThree;
import com.eden.enforcementService.common.response.DashboardSecTwo;
import com.eden.enforcementService.payload.ApiResponse;
import com.eden.enforcementService.service.CitationService;
import com.eden.enforcementService.util.Constants;
import com.google.common.net.HttpHeaders;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/citations")
public class CitationsController {

    @Autowired
    private CitationService citationService;

    @PostMapping(value = "/add-multiple")
    @PreAuthorize("hasAuthority('AddCitations')")
    public ApiResponse<List<AddingCitationResponseDto>> addCitations(@Valid @RequestBody CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception {
        return ApiResponse.ok(citationService.addCitations(citationAddingMultipleRequest));
    }

    @GetMapping("/unpaidCitations")
    @PreAuthorize("hasAuthority('ViewCitations')")
    public ApiResponse<PaginationDto> unpaidCitations(@Valid ViewCitationsRequest request) {
        PaginationDto unpaidCitations = citationService.getUnpaidCitations(request);
        return ApiResponse.ok(unpaidCitations);
    }

    @PutMapping("/voidCitations")
    @PreAuthorize("hasAuthority('VoidCitations')")
    // must have access to settle && void
    public ApiResponse voidCitationById(@Valid VoidCitationsRequest request) {
        citationService.voidCitation(request);
        return ApiResponse.ok(Constants.SuccessMessages.CITATION_VOIDED);
    }

    @GetMapping("/citationsDetails")
    @PreAuthorize("hasAuthority('ViewCitations')")
    public ApiResponse<List<CitationDto>> citationsDetailsByIds(@Valid citationsDetailsRequest request) {
        List<CitationDto> citationDtos = citationService.citationDetails(request);
        return ApiResponse.ok(citationDtos);
    }

    @PutMapping("/payCitations")
    @PreAuthorize("hasAuthority('SettleCitations') and hasAuthority('VoidCitations')")
    public ApiResponse<InvoiceDto> payCitations(@RequestHeader(org.springframework.http.HttpHeaders.AUTHORIZATION) String authorizationHeader, @Valid @RequestBody HandelCitationRequestList request) {
        InvoiceDto invoiceDto = this.citationService.handleCitations(authorizationHeader, request);
        return ApiResponse.ok(invoiceDto);
    }

    @PutMapping("/settleCitations")
    @PreAuthorize("hasAuthority('SettleCitations') and hasAuthority('VoidCitations')")
    public ApiResponse<InvoiceDto> settleCitations(@RequestHeader(org.springframework.http.HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody SettleRequest request) {
        System.out.println("in Settle Citation");
        InvoiceDto invoiceDto = this.citationService.settleCitations(authorizationHeader, request.getCitationIds());
        return ApiResponse.ok(invoiceDto);
    }

    @GetMapping("/export-citations")
    @PreAuthorize("hasAuthority('ExportCitations')")
    public void exportCSV(HttpServletResponse response, @Valid ExportCitationsReq request) throws Exception {

        // set file name and content type
        String filename = "citations.csv";
        response.setContentType("text/csv");

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        // create a csv writer
        StatefulBeanToCsv<CitationExportDto.CitationExportDtoBuilder> writer =
                new StatefulBeanToCsvBuilder
                        <CitationExportDto.CitationExportDtoBuilder>(response.getWriter())
                        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).
                        withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withOrderedResults(false).build();

        List<CitationDto> citationDtos = citationService.fetchAll(request.getCitationsIds());
//        List<CitationDto> citationDtos = citationService.fetchAll();
        List<CitationExportDto.CitationExportDtoBuilder> collect = citationDtos.stream().map(citationDto -> {

            OperationViolationPenaltiesDTO operationViolationPenalty = citationDto.getCitationPenalties().get(0).getOperationViolationPenalty();

            String violationEName = operationViolationPenalty.getViolation().getEnName();
            CitationExportDto.CitationExportDtoBuilder citationExportDtoBuilder = CitationExportDto.builder()
                    .id(citationDto.getId())
                    .plateNumberEn(citationDto.getPlateNumberEn())
                    .violationName(violationEName)
                    .penaltyEnName(operationViolationPenalty.getPenalty().getEnName())
                    .penaltyArName(operationViolationPenalty.getPenalty().getArName())
                    .penaltyType(operationViolationPenalty.getPenalty().getType())
                    .penaltyFees(operationViolationPenalty.getPenalty().getFees());
            return citationExportDtoBuilder;
//                    .penaltyMethod(operationViolationPenalty.getPenalty().getMethod());
        }).collect(Collectors.toList());
        writer.write(collect);

    }

    @GetMapping("/dashboard_Sec_One")
    @PreAuthorize("hasAuthority('ViewDashboard')")
    public ApiResponse<DashboardSecOne> dashboard_Sec_One(DashSecOneRequest request) throws Exception {
        DashboardSecOne dashboardSecOne = this.citationService.dashboardSecOne(request);
        return ApiResponse.ok(dashboardSecOne);
    }

    @GetMapping("/dashboard_Sec_Two")
    @PreAuthorize("hasAuthority('ViewDashboard')")
    public ApiResponse<DashboardSecTwo> dashboard_Sec_Two(DashSecTwoRequest request) throws Exception {
        DashboardSecTwo dashboardSecOne = this.citationService.dashboardSecTwo(request);
        return ApiResponse.ok(dashboardSecOne);
    }

    @GetMapping("/dashboard_Sec_Three")
    @PreAuthorize("hasAuthority('ViewDashboard')")
    public ApiResponse<DashboardSecThree> dashboard_Sec_Three(DashSecThreeRequest request) throws Exception {
        DashboardSecThree dashboardSecThree = this.citationService.dashboardSecThree(request);
        return ApiResponse.ok(dashboardSecThree);
    }

    @GetMapping("/dashboard_Sec_Three_One")
    @PreAuthorize("hasAuthority('ViewDashboard')")
    public ApiResponse<DashboardSecThreeOne> dashboard_Sec_Three_One(DashSecThreeOneRequest request) throws Exception {
        DashboardSecThreeOne dashboardSecOne = this.citationService.dashboardSecThreeOne(request);
        return ApiResponse.ok(dashboardSecOne);
    }

    @GetMapping("/openCitations")
//    @PreAuthorize("hasAuthority('ViewCitations')")
    public ApiResponse<OpenCitationDto> openCitations(@Valid OpenCitationsRequest request) {
        OpenCitationDto openCitations = citationService.getOpenCitations(request);
        return ApiResponse.ok(openCitations);
    }

    @GetMapping("/checkLegality/{enPlateNumber}")
    public Object checkLegality(@PathVariable String enPlateNumber, @RequestParam("latitude") Double latitude,
                                @RequestParam("longitude") Double longitude, @RequestParam("streetName") String streetName,
                                @RequestParam("shiftWorkForceId") Long shiftWorkForceId,
                                @RequestParam("operationName") String operationName) {
        return ApiResponse.ok(this.citationService.checkVehicleLegality(enPlateNumber, latitude, longitude, streetName, shiftWorkForceId, operationName));
    }

    @GetMapping("/isLegalParking/{enPlateNumber}")
    public Object isLegalParking(@PathVariable String enPlateNumber, VehicleLegalityRequest vehicleLegalityRequest) {
        return ApiResponse.ok(this.citationService.isLegalParking(enPlateNumber, vehicleLegalityRequest));
    }

    @GetMapping("/listCitations/mobile")
    @PreAuthorize("hasAuthority('ViewCitations')")
    public ApiResponse<PaginationDto> listCitationForMobile(@Valid ListCitationsForMobileRequest request) {
        PaginationDto paginationDto = citationService.listCitationForMobile(request);
        return ApiResponse.ok(paginationDto);
    }

    @GetMapping("/searchCitations")
    @PreAuthorize("hasAuthority('ViewCitations')")
    public ApiResponse<PaginationDto> searchCitation(@Valid SearchCitationsRequest request) {
        PaginationDto paginationDto = citationService.searchCitation(request);
        return ApiResponse.ok(paginationDto);
    }

}
