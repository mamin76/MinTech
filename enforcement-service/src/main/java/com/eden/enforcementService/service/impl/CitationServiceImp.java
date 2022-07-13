package com.eden.enforcementService.service.impl;

import com.eden.enforcementService.clients.*;
import com.eden.enforcementService.clients.dtos.*;
import com.eden.enforcementService.common.dto.*;
import com.eden.enforcementService.common.mapper.CitationMapper;
import com.eden.enforcementService.common.model.entity.*;
import com.eden.enforcementService.common.model.enums.*;
import com.eden.enforcementService.common.request.*;
import com.eden.enforcementService.common.response.DashboardSecThree;
import com.eden.enforcementService.common.response.DashboardSecTwo;
import com.eden.enforcementService.common.specs.CitaionSpecs;
import com.eden.enforcementService.config.Properties;
import com.eden.enforcementService.customemailbuilder.CustomEmailBuilder;
import com.eden.enforcementService.exception.BusinessException;
import com.eden.enforcementService.metric.clients.AuthenticateClient;
import com.eden.enforcementService.metric.clients.IsPlateExpiredClient;
import com.eden.enforcementService.metric.gen.AuthenticateResponse;
import com.eden.enforcementService.metric.gen.IsPlateExpiredResponse;
import com.eden.enforcementService.producers.citationkpi.CitationKpiProducer;
import com.eden.enforcementService.producers.citationkpi.dtos.CitationKpi;
import com.eden.enforcementService.producers.email.EmailProducer;
import com.eden.enforcementService.producers.email.dtos.Email;
import com.eden.enforcementService.producers.scankpi.ScanKpiProducer;
import com.eden.enforcementService.producers.scankpi.dtos.ScanKpi;
import com.eden.enforcementService.repository.CitationRepository;
import com.eden.enforcementService.service.*;
import com.eden.enforcementService.service.validators.CitationValidator;
import com.eden.enforcementService.util.Constants;
import com.eden.enforcementService.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.eden.enforcementService.common.model.enums.PenalityStatus.Settled;
import static com.eden.enforcementService.common.model.enums.PenaltyMethod.MONETARY;
import static com.eden.enforcementService.util.Constants.Sorting.CREATION_DATE;

@Service
@Primary
public class CitationServiceImp implements CitationService {

    @Autowired
    private AuthenticateClient authenticateClient;
    @Autowired
    private IsPlateExpiredClient isPlateExpiredClient;

    @Autowired
    private CitationRepository citationRepository;

    @Autowired
    private CitationPenaltiesService citationPenaltiesService;

    @Autowired
    private CitationImageService citationImageService;

    @Autowired
    private CitationMapper citationMapper;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private CitationKpiProducer citationKpiProducer;

    @Autowired
    private CitationValidator citationValidator;

    @Autowired
    private InvoiceFeignClient invoiceFeignClient;

    @Autowired
    private OperationFeignClient operationFeignClient;


    @Autowired
    private VehicleCaller vehicleCaller;

    @Autowired
    private OperationCaller operationCaller;

    @Autowired
    private ScanKpiProducer scanKpiProducer;

    @Autowired
    private CustomEmailBuilder<OpenCitationDto> bootAndTowEmailBuilder;

    @Autowired
    private CustomEmailBuilder<OpenCitationDto> bootAndTowEmailArabicBuilder;

    @Autowired
    private CustomEmailBuilder<NewCitationDto> newCitationEmailBuilder;

    @Autowired
    private CustomEmailBuilder<NewCitationDto> newCitationEmailArabicBuilder;

    @Autowired
    private CustomEmailBuilder<BlackListMailDto> blackListEmailBuilder;

    @Autowired
    private Properties properties;

    @Autowired
    private ThakiCheckPlateNumberFeignClient thakiCheckPlateNumberFeignClient;

    @Autowired
    private BlackListedVehicleService blackListedVehicleService;

    @Autowired
    private BlackListPenaltiesService blackListPenaltiesService;


    @Autowired
    private WhiteListedVehicleService whiteListedVehicleService;

    @Autowired
    private ViolationService violationService;

    @Autowired
    private ReasonService reasonService;

    @Autowired
    private PenaltyService penaltyService;


    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public List<AddingCitationResponseDto> addCitations(CitationAddingMultipleRequest citationAddingMultipleRequest) throws Exception {
        List<AddingCitationResponseDto> addingCitationResponseDtos = new LinkedList();
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Map<Long, OperationDto> operationMap = operationCaller.getAllOperations(Utils.getHeaderFromRequest(HttpHeaders.AUTHORIZATION).toString());

        if (!operationMap.containsKey(citationAddingMultipleRequest.getOperationId())) {
            throw new BusinessException(Constants.ErrorKeys.OPERATION_NOT_EXISTS, HttpStatus.BAD_REQUEST);
        }
        citationAddingMultipleRequest.setOperationName(operationMap.get(citationAddingMultipleRequest.getOperationId()).getName());
        citationValidator.validate(citationAddingMultipleRequest);
        AddingCitationResponseDto addingCitationResponseDto = null;
        for (CitationRequest citationRequest : citationAddingMultipleRequest.getCitations()) {
            citationRequest.setCreatedBy(userName);
            addingCitationResponseDto = addCitation(citationRequest, citationAddingMultipleRequest.getOperationId(),
                    citationAddingMultipleRequest.getStreetName(), citationAddingMultipleRequest.getOperationName());
            addingCitationResponseDtos.add(addingCitationResponseDto);
        }

        List<CitationRequest> citations = citationAddingMultipleRequest.getCitations();
        Long shiftWorkforceId = null;
        if (Objects.nonNull(citations) && !citations.isEmpty()) {
            CitationRequest citationRequest = citations.get(0);
            if (Objects.nonNull(citationRequest)) {
                shiftWorkforceId = citationRequest.getShiftWorkforceId();
            }
        }
        citationKpiProducer.publishCitationKpi(CitationKpi.builder().
                shiftWorkForceId(shiftWorkforceId).
                citationCount(citationAddingMultipleRequest.getCitations().size()).build());
        return addingCitationResponseDtos;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    private AddingCitationResponseDto addCitation(CitationRequest citationRequest, Long operationId, String streetName, String operationName) {
        AddingCitationResponseDto addingCitationResponseDto = new AddingCitationResponseDto();
        List<CitationPenalties> citationPenalties = citationPenaltiesService.buildCitationPenaltiesByOperationAndViolation(operationId, citationRequest.getViolationId(), addingCitationResponseDto);

        Citation citation = citationMapper.toCitation(citationRequest);
        citation.setStreetName(streetName);
        citation.setOperationId(operationId);
        citation = citationRepository.save(citation);
        Violation violation = violationService.getViolationById(citationRequest.getViolationId());

        addingCitationResponseDto.setId(citation.getId());
        addingCitationResponseDto.setCreatedAt(LocalDateTime.now());
        addingCitationResponseDto.setArName(violation.getArName());
        addingCitationResponseDto.setEnName(violation.getEnName());
        addingCitationResponseDto.setDescription(violation.getEnDescription());

        Citation finalCitation = citation;
        citationPenalties.forEach(citationPenalty -> {
            citationPenalty.setCreatedBy(citationRequest.getCreatedBy());
            citationPenalty.setCitation(finalCitation);
            citationPenalty.setShiftWorkforceId(finalCitation.getShiftWorkforceId());
        });

        citationPenalties = citationPenaltiesService.saveCitationPenalties(citationPenalties);

        citationImageService.updateEvidenceWithTheNewCitationId(citationRequest.getEvidenceIds(), citation.getId());

        sendPenaltiesEmail(citationPenalties, citation, operationName, violation.getEnName(), violation.getId());
        return addingCitationResponseDto;
    }

    @Transactional
    @Override
    public PaginationDto getUnpaidCitations(ViewCitationsRequest request) {

        Pageable pageable = getPageRequestSortedByField(request);

        Specification<Citation> specification = getCitationSpecs(request);
        Page<Citation> citationPage = this.citationRepository.findAll(specification, pageable);
        List<Citation> citations = citationPage.getContent();
        HashSet<Citation> citationsSet = new HashSet<>(citations);
        RetrievingSimpleVehicleRequest retrievingSimpleVehicleRequest = new RetrievingSimpleVehicleRequest();
        List<RetrievingSimpleVehicleDto> retrievingSimpleVehicleDtos = new LinkedList<>();
        retrievingSimpleVehicleRequest.setRetrievingSimpleVehicleDtos(retrievingSimpleVehicleDtos);

        Map<Long, List<Long>> citationImgs = citationsSet.stream().collect(
                Collectors.toMap(Citation::getId,
                        citation -> citation.getCitationImages().stream()
                                .map(citationImage -> citationImage.getId())
                                .collect(Collectors.toList())));

        Set<CitationDto> citationDtosSet = this.citationMapper.toDtoList(citationsSet);
        Map<Long, OperationDto> operationMap = operationCaller.getAllOperations(Utils.getHeaderFromRequest(HttpHeaders.AUTHORIZATION).toString());
        citationDtosSet.forEach(citationDto -> {
            retrievingSimpleVehicleDtos.add(RetrievingSimpleVehicleDto.builder().
                    countryId(citationDto.getCountryId()).
                    plateNumberEn(citationDto.getPlateNumberEn()).build());

            List<CitationPenaltiesDTO> citationPenalties = citationDto.getCitationPenalties();
            if (Objects.nonNull(citationPenalties) && !citationPenalties.isEmpty()) {
                CitationPenaltiesDTO citationPenaltiesDTO = citationPenalties.get(0);
                if (Objects.nonNull(citationPenaltiesDTO)) {
                    String enName = citationPenaltiesDTO.getOperationViolationPenalty().getViolation().getEnName();
                    citationDto.setViolationName(enName);
                    String arName = citationPenaltiesDTO.getOperationViolationPenalty().getViolation().getArName();
                    citationDto.setViolationNameAr(arName);
                    citationDto.setImagesIds(citationImgs.get(citationDto.getId()));
                    citationDto.setOperationId(citationPenaltiesDTO.getOperationViolationPenalty().getOperationId());
                    citationDto.setOperationName(operationMap.get(citationDto.getOperationId()) == null ? null : operationMap.get(citationDto.getOperationId()).getName());
                }

                Double amount = citationPenalties.stream().map(CitationPenaltiesDTO::getOperationViolationPenalty)
                        .map(OperationViolationPenaltiesDTO::getPenalty)
                        .filter(penaltyDto -> penaltyDto.getMethod().equals(MONETARY))
                        .map(PenaltyDto::getFees)
                        .reduce(0.0, (a, b) -> a + b);
                citationDto.setAmount(amount);
            }
        });
        Map<String, CitationVehicleDto> vehiclesMap = vehicleCaller.getVehicle(retrievingSimpleVehicleRequest, Utils.getHeaderFromRequest(HttpHeaders.AUTHORIZATION).toString());
        if (Objects.nonNull(vehiclesMap) && !vehiclesMap.isEmpty()) {
            citationDtosSet.forEach(citationDto -> {
                CitationVehicleDto v = vehiclesMap.get(citationDto.getPlateNumberEn() + "|" + citationDto.getCountryId());
                citationDto.setCitationVehicleDto(v);
            });
        }
        PaginationDto paginationDto = PaginationDto.builder()
                .content(new ArrayList<>(citationDtosSet))
                .totalPages(citationPage.getTotalPages())
                .totalElements(citationPage.getTotalElements())
                .build();

        return paginationDto;

    }

    @Transactional
    @Override
    public void voidCitation(VoidCitationsRequest request) {
        Long id = request.getId();
        String reason = request.getReason();
        Long reasonId = request.getReasonId();

        Citation citation = getCitation(id);
        citation.setStatus(CitationStatus.Voided);
        citation.setModifiedDate(LocalDateTime.now());
        this.citationRepository.save(citation);

        Reason reasonObj = this.reasonService.getReason(reasonId, ReasonType.VOID);
        List<CitationPenalties> citationPenalties = this.citationPenaltiesService.getCitationPenalties(id);

        List<CitationPenalties> openPenalties = citationPenalties.stream()
                .filter(citationPenaltie -> citationPenaltie.getStatus().equals(PenalityStatus.Opened))
                .collect(Collectors.toList());

        this.citationPenaltiesService.voidCitationPenalties(openPenalties, reason, reasonObj);

    }

    private Citation getCitation(Long id) {
        Optional<Citation> optionalCitation = this.citationRepository.findById(id);
        if (!optionalCitation.isPresent()) {
            throw new BusinessException(Constants.ErrorKeys.CITATION_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return optionalCitation.get();
    }

    @Override
    public List<CitationDto> citationDetails(citationsDetailsRequest request) {
        List<Long> citationIds = request.getCitationIds();

        if (Objects.isNull(citationIds) || citationIds.size() == 0)
            throw new BusinessException(Constants.ErrorKeys.EMPTY_CITATION_ID, HttpStatus.NOT_ACCEPTABLE);

        List<Citation> citations = citationIds
                .stream()
                .map(cid -> {
                    Optional<Citation> citationById = this.citationRepository.findById(cid);
                    return citationById.get();
                }).collect(Collectors.toList());

        List<CitationDto> citationDtos = this.citationMapper.toDtoList(citations);

        // Remove Email From List Of Actions
        List<CitationPenaltiesDTO> penalties = new ArrayList<>();

        citationDtos.forEach(citationDto -> {
            List<CitationPenaltiesDTO> citationPenalties = citationDto.getCitationPenalties();
            if (Objects.nonNull(citationPenalties) && !citationPenalties.isEmpty()) {

                String enName = citationPenalties.get(0).getOperationViolationPenalty().getViolation().getEnName();
                citationDto.setViolationName(enName);
                List<CitationPenaltiesDTO> citationPenalties1 = citationDto.getCitationPenalties();
                citationPenalties1.forEach(citationPenaltiesDTO -> {
                    PenaltyMethod method = citationPenaltiesDTO.getOperationViolationPenalty().getPenalty().getMethod();
                    if (!method.equals(MONETARY)) {
                        penalties.add(citationPenaltiesDTO);
                    }
                });
                citationPenalties1.removeAll(penalties);
                citationDto.setCitationPenalties(citationPenalties1);
            }
        });

        return citationDtos;
    }


    @Transactional
    @Override
    public InvoiceDto handleCitations(String authorizationHeader, HandelCitationRequestList request) {

        // if( all action are voided -> citation will be void and same for settle "cit" TB
        // in CIT_PEN reflect changes on each action
        List<CitationPenalties> penaltiesToInvoice = new ArrayList<>();

        List<HandelCitationRequest> citationRequests = request.getCitationRequests();
//        List<Long> cIds = citationRequests.stream().map(HandelCitationRequest::getId).collect(Collectors.toList());
        List<InvoiceItemRequest> invoiceItemRequests = new ArrayList<>();

        List<Citation> setteledCitations = new ArrayList<>();
        Set<Long> settledCitationIds = new LinkedHashSet<>();
        citationRequests.stream().forEach(handelCitationRequest -> {
            Citation citation = this.getCitation(handelCitationRequest.getId());
            if (citation.getStatus().equals(CitationStatus.Settled)) {
                throw new BusinessException(Constants.ErrorKeys.CITATION_ALREADY_SETTLED, HttpStatus.NOT_ACCEPTABLE);
            }
            List<String> penaltyStatuses = handelCitationRequest.getPenaltiesRequestList().stream()
                    .map(CitationPenaltiesRequest::getPenActionType).collect(Collectors.toList());

            boolean voidCase = penaltyStatuses.stream().allMatch(s -> s.equalsIgnoreCase(PenActionType.Voided.getValue()));
            boolean settleCase = penaltyStatuses.stream().allMatch(s -> s.equalsIgnoreCase(PenActionType.Settled.getValue()));
            boolean settleBulkCase = penaltyStatuses.stream().anyMatch(s -> s.equalsIgnoreCase(PenActionType.Settled.getValue()));

            if (voidCase) {
                citation.setStatus(CitationStatus.Voided);
            } else if (settleCase) {
                citation.setStatus(CitationStatus.Settled);
            } else if (settleBulkCase) {
                citation.setStatus(CitationStatus.Settled);
            }

            //update Citation And it's penalties
            citation.setModifiedDate(LocalDateTime.now());
            this.citationRepository.save(citation);

            List<CitationPenaltiesRequest> penaltiesRequestList = handelCitationRequest.getPenaltiesRequestList();
            penaltiesRequestList.stream().forEach(citPenRequest -> {
                CitationPenalties citationPenalties = this.citationPenaltiesService.findCitPenById(citPenRequest.getCit_Pen_Id());
                citationPenalties.setComment(citPenRequest.getComment());
                String penActionType = citPenRequest.getPenActionType();
                PenalityStatus status = PenalityStatus.valueOf(penActionType);
                //check if setteled put object to send to invoice
                citationPenalties.setStatus(status);
                citationPenalties.setModifiedDate(LocalDateTime.now());
                Long count = citPenRequest.getCount();
                //penActionType.equalsIgnoreCase(PenActionType.Settled.getValue())  &&


                if (Objects.isNull(count))
                    count = 1l;
                if (count <= 0)
                    throw new BusinessException(Constants.ErrorKeys.INVALID_ACTION_COUNT, HttpStatus.NOT_ACCEPTABLE);
                citationPenalties.setActionCount(count);
                this.citationPenaltiesService.updateCitationPenalty(citationPenalties);
                if (status.equals(Settled)) {
                    if (citationPenalties.getOperationViolationPenalty().getPenalty().getMethod().equals(MONETARY)) {
                        penaltiesToInvoice.add(citationPenalties);
                        setteledCitations.add(citation);
                        settledCitationIds.add(handelCitationRequest.getId());
                    }
                }
            });

            //send email if required
            List<CitationPenalties> citationPenalties = this.citationPenaltiesService.getCitationPenalties(citation.getId());
//            sendPenaltiesEmail(citationPenalties, citation, op);

            //Prepare Calling Invoice.
            invoiceItemRequests.addAll(penaltiesToInvoice.stream()
                    .map(citationPenalties1 -> {
                        Penalty penalty = citationPenalties1.getOperationViolationPenalty().getPenalty();
                        InvoiceItemRequest quantity = InvoiceItemRequest.builder().itemName(penalty.getEnName())
                                .amount(BigDecimal.valueOf(penalty.getFees()))
                                .quantity(citationPenalties1.getActionCount().intValue()).build();
                        return quantity;
                    }).collect(Collectors.toList()));
        });

        if (CollectionUtils.isNotEmpty(invoiceItemRequests)) {

            // add feign client Call
            InvoiceRequest invoiceRequest = InvoiceRequest.builder()
                    .items(invoiceItemRequests)
                    .paymentMethod("PortalWFSettlement")
                    .source("Enforcement Portal")
                    .comment("")
                    .description("تحصيل مخالفة رقم " + settledCitationIds)
                    .build();


            ApiResponse<InvoiceDto> invoiceRes = invoiceFeignClient.addInvoice(authorizationHeader, invoiceRequest);
            Long invoiceNumber = invoiceRes.getPayload().getInvoiceNumber();
            setteledCitations.forEach(citation -> {
                citation.setInvoiceNumber(invoiceNumber);
                this.citationRepository.save(citation);
                System.out.println(citation.getCreatedDate().getMonth());
                System.out.println(citation.getCreatedDate().getMonthValue());
            });

            return invoiceRes.getPayload();
        } else {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setVoided(true);
            invoiceDto.setVoidedMsg("Void successfully");
            return invoiceDto;
        }

    }

    @Override
    public List<CitationDto> fetchAll(List<Long> citationsIds) {
        List<Citation> all = new ArrayList<>();
        if (!Objects.isNull(citationsIds) && !citationsIds.isEmpty()) {
            List<Citation> citationRepositoryByIdIn = this.citationRepository.findByIdIn(citationsIds);
            all.addAll(citationRepositoryByIdIn);
        } else {
            all.addAll(this.citationRepository.findAll());
        }
        List<CitationDto> citationDtos = this.citationMapper.toDtoList(all);
        return citationDtos;
    }

    @Override
    public DashboardSecOne dashboardSecOne(DashSecOneRequest request) {

        String byMonth = request.getByMonth();
        Long operationId = request.getOperationId();

        List<CitationPenaltiesProjection> byMonthAndOperation = this.citationPenaltiesService.findByMonthAndOperation(byMonth, operationId);

        Double sumOfPaidCitations = getSumOfPaidCitations(byMonthAndOperation);
        Double sumOfCitations = getSumOfCitations(byMonthAndOperation);

        DashboardSecOne dashboardSecOne = DashboardSecOne.builder()
                .sumOfPaidCitations(sumOfPaidCitations)
                .sumOfCitations(sumOfCitations)
                .build();
        return dashboardSecOne;
    }

    @Override
    public DashboardSecTwo dashboardSecTwo(DashSecTwoRequest request) {
        Long operationId = request.getOperationId();

        List<CitationPenaltiesProjection> byMonthAndOperation = this.citationPenaltiesService
                .findCitationsByOperation(operationId);

        Double sumOfPaidCitations = getSumOfPaidCitations(byMonthAndOperation);
        Double sumOfCitations = getSumOfCitations(byMonthAndOperation);

        ApiResponse<ActiveShiftAndAssignedWorkforces> countShiftsAndWorkforces = this.operationFeignClient.getCountShiftsAndWorkforces();


        ActiveShiftAndAssignedWorkforces payload = countShiftsAndWorkforces.getPayload();
        DashboardSecTwo dashboardSecOne = DashboardSecTwo.builder()
                .sumOfPaidCitations(sumOfPaidCitations)
                .sumOfCitations(sumOfCitations)
                .activeShifts(payload.getActiveShifts())
                .assignedWorkforces(payload.getAssignedWorkforces())
                .build();
        return dashboardSecOne;
    }

    @Override
    public DashboardSecThree dashboardSecThree(DashSecThreeRequest request) {

        List<String> months = request.getMonths();

        List<CitationPenaltiesProjection> byMonthAndOperation = this.citationPenaltiesService.findByMonths(months);

        Double openCitations = getSumOfOpenCitations(byMonthAndOperation);
        Double paidCitations = getSumOfPaidCitations(byMonthAndOperation);
        Double voidedCitations = getSumOfVoidedCitations(byMonthAndOperation);

        DashboardSecThree dashboardSecThree = DashboardSecThree.builder()
                .openCitations(openCitations)
                .paidCitations(paidCitations)
                .voidedCitations(voidedCitations)
                .build();
        return dashboardSecThree;
    }

    @Override
    public boolean checkVehicleLegality(String enPlateNumber, Double latitude, Double longitude, String streetName, Long shiftWorkForceId, String operationName) {

        sendScanKPI(shiftWorkForceId);
        OpenCitationsRequest request = getOpenCitationsRequest(enPlateNumber, latitude, longitude, streetName, operationName);
        sendMailOpenCitations(request);//TODO SHOULD BE FIRST THING IN FUNCTION

        AuthenticateResponse authenticateResponse = authenticateClient.getAuth();
        IsPlateExpiredResponse isPlateExpiredResponse = isPlateExpiredClient.getLegality(authenticateResponse.getAuthenticateResult(), enPlateNumber);
        //TODO CHECK BLACK LIST , WHITE LIST
        if (!isPlateExpiredResponse.isIsPlateExpiredResult()) {
            return false;
        }

        ThakiCheckLegalDto thakiLegalityResponse = thakiCheckPlateNumberFeignClient.getThakiCheckLegalDto(properties.getThakiCheckToken(),
                enPlateNumber);
        return Objects.equals(thakiLegalityResponse.getMessage(), "illegal parking");
    }

    @Override
    public VehicleLegalityResponse isLegalParking(String enPlateNumber, VehicleLegalityRequest vehicleLegalityRequest) {
        VehicleStatus vehicleStatus = VehicleStatus.NORMAL;
        sendScanKPI(vehicleLegalityRequest.getShiftWorkForceId());
        boolean blacklistedVehicle = isBlackListVehicle(enPlateNumber, vehicleLegalityRequest);
        boolean legal = false;
        if (blacklistedVehicle) {
            return VehicleLegalityResponse.builder().legal(legal).vehicleStatus(VehicleStatus.BLACKLIST).build();
        }

        AuthenticateResponse authenticateResponse = authenticateClient.getAuth();
        IsPlateExpiredResponse isPlateExpiredResponse = isPlateExpiredClient.getLegality(authenticateResponse.getAuthenticateResult(), enPlateNumber);
        //TODO CHECK BLACK LIST , WHITE LIST
        if (!isPlateExpiredResponse.isIsPlateExpiredResult()) {
            legal = true;
        } else {
            ThakiCheckLegalDto thakiLegalityResponse = thakiCheckPlateNumberFeignClient.getThakiCheckLegalDto(properties.getThakiCheckToken(),
                    enPlateNumber);
            legal = !Objects.equals(thakiLegalityResponse.getMessage(), "illegal parking");
        }

        if (!legal) {
            boolean isWhiteListedVehicle = whiteListedVehicleService.isCurrentlyExistWhitelistVehicle(enPlateNumber, vehicleLegalityRequest.getCountryName());
            if (isWhiteListedVehicle) {
                vehicleStatus = VehicleStatus.WHITELIST;
                legal = true;
            }
        }
        if (!vehicleStatus.equals(VehicleStatus.WHITELIST)) {
            OpenCitationsRequest request = getOpenCitationsRequest(enPlateNumber, vehicleLegalityRequest.getLatitude(), vehicleLegalityRequest.getLongitude(), vehicleLegalityRequest.getStreetName(), vehicleLegalityRequest.getOperationName());
            sendMailOpenCitations(request);//TODO SHOULD BE FIRST THING IN FUNCTION
        }

        return VehicleLegalityResponse.builder().
                legal(legal).
                vehicleStatus(vehicleStatus).build();
    }

    @Override
    public PaginationDto listCitationForMobile(ListCitationsForMobileRequest request) {
        if (StringUtils.isEmpty(request.getPlateNumber())) {
            throw new BusinessException(Constants.ErrorKeys.EMPTY_PLATE_NUMBER, HttpStatus.BAD_REQUEST);
        }
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

        Page<CitationsProjection> result = citationRepository.getCitationByPlateNumber(request.getPlateNumber(), pageable);
        return PaginationDto.builder()
                .content(result.getContent())
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }

    private boolean isBlackListVehicle(String enPlateNumber, VehicleLegalityRequest vehicleLegalityRequest) {
        boolean result = false;
        BlackListedVehicle blackListedVehicle = blackListedVehicleService.getBlackListedVehicleByEnPlateNumberAndCountryName(enPlateNumber, vehicleLegalityRequest.getCountryName());
        result = Objects.nonNull(blackListedVehicle);
        if (result) {
            PenaltyDto penaltyDto = penaltyService.getPenaltyByEnName(Constants.enName);

            if (penaltyDto.getMethod().equals(PenaltyMethod.EMAIL)) {

                BlackListMailDto blackListMailDto = BlackListMailDto.builder().latitude(vehicleLegalityRequest.getLatitude())
                        .longitude(vehicleLegalityRequest.getLongitude())
                        .latitude(vehicleLegalityRequest.getLatitude())
                        .body(penaltyDto.getBody())
                        .plateNumberEn(enPlateNumber)
                        .operationName(vehicleLegalityRequest.getOperationName())
                        .countryName(vehicleLegalityRequest.getCountryName())
                        .streetName(vehicleLegalityRequest.getStreetName())
                        .build();

                String body = blackListEmailBuilder.buildBody(blackListMailDto);

                Email email = Email.builder().
                        toEmail(penaltyDto.getEmail()).
                        htmlFormat(true).
                        body(body).
                        subject(penaltyDto.getSubject().replace("${plate}", blackListMailDto.getPlateNumberEn())).build();
                try {
                    emailProducer.publishEmail(email);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    private OpenCitationsRequest getOpenCitationsRequest(String enPlateNumber, Double latitude, Double longitude, String streetName, String operationName) {
        OpenCitationsRequest request = new OpenCitationsRequest();
        request.setAddress(streetName);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setPlateNumberEn(enPlateNumber);
        request.setOperationName(operationName);
        return request;
    }

    private void sendScanKPI(Long shiftWorkForceId) {
        try {
            scanKpiProducer.publishScanKpi(ScanKpi.builder().scanCount(1).shiftWorkForceId(shiftWorkForceId).build());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OpenCitationDto getOpenCitations(OpenCitationsRequest request) {
        String plateNumberEn = request.getPlateNumberEn();
        CitationStatus status = CitationStatus.Opened;
        List<Citation> citations = this.citationRepository.findByPlateNumberEnAndStatus(plateNumberEn, status);
        List<CitationDto> citationDtos = this.citationMapper.toDtoList(citations);
        List<OpenCitationPenaltiesDto> penaltiesDto = new ArrayList<>();
        List<OpenCitations> openCitationsList = new ArrayList<>();

        citationDtos.forEach(citationDto -> {
            List<CitationPenaltiesDTO> citationPenalties = citationDto.getCitationPenalties();
            if (Objects.nonNull(citationPenalties) && !citationPenalties.isEmpty()) {
                ViolationDto violationDto = citationPenalties.get(0).getOperationViolationPenalty().getViolation();
                citationDto.setViolationName(violationDto.getEnName());
                citationDto.setViolationNameAr(violationDto.getArName());
                citationDto.setViolationId(violationDto.getId());
            }
            List<PenaltyDto> penaltyDtos = citationDto.getCitationPenalties().stream()
                    .filter(citationPenaltiesDTO -> citationPenaltiesDTO.getStatus().equals(PenalityStatus.Opened))
                    .map(CitationPenaltiesDTO::getOperationViolationPenalty)
                    .map(OperationViolationPenaltiesDTO::getPenalty)
                    .collect(Collectors.toList());


            List<OpenCitationPenaltiesDto> collect = penaltyDtos.stream()
                    .map(penaltyDto -> {
                        OpenCitationPenaltiesDto penaltiesDto1 = OpenCitationPenaltiesDto.builder()
                                .enName(penaltyDto.getEnName())
                                .arName(penaltyDto.getArName())
                                .fees(penaltyDto.getFees())
                                .build();
                        return penaltiesDto1;
                    }).collect(Collectors.toList());

            openCitationsList.add(OpenCitations.builder()
                    .id(citationDto.getId())
                    .violationName(citationDto.getViolationName())
                    .violationNameAr(citationDto.getViolationNameAr())
                    .violationId(citationDto.getViolationId())
                    .createdDate(citationDto.getCreatedDate())
                    .citationPenalties(collect).build());

        });

        OpenCitationDto openCitationDto = OpenCitationDto.builder()
                .address(request.getAddress())
                .plateNumberEn(request.getPlateNumberEn())
                .citations(openCitationsList).operationName(request.getOperationName()).
                longitude(request.getLongitude()).
                latitude(request.getLatitude()).build();

        return openCitationDto;
    }

    @Override
    public DashboardSecThreeOne dashboardSecThreeOne(DashSecThreeOneRequest request) {

        Map<Long, Double> revenue = this.citationPenaltiesService.getRevenueStatisticsByYear(request.getYear());

        return DashboardSecThreeOne.builder()
                .revenue(revenue)
                .build();
    }

    @Override
    @Async("openedCitationMailExecutor")
    public void sendMailOpenCitations(OpenCitationsRequest request) {
        OpenCitationDto openCitationDto = getOpenCitations(request);
        sendMailOpenCitations(request, openCitationDto);
    }

    private void sendMailOpenCitations(OpenCitationsRequest request, OpenCitationDto openCitationDto) {
        if (!openCitationDto.getCitations().isEmpty()) {
            String body = bootAndTowEmailArabicBuilder.buildBody(openCitationDto);

            Email email = Email.builder().
                    toEmail(properties.getEnforcementTeamMail()).
//                    toEmail("z.osama@eden-tech.io").
        htmlFormat(true).
                    body(body).
                    subject("مخالفات سابقة غير مسددة").build();

            try {
                emailProducer.publishEmail(email);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    private Double getSumOfVoidedCitations(List<CitationPenaltiesProjection> byMonthAndOperation) {
        double sum = byMonthAndOperation.stream().map(cp -> {
            double penaltyTotal = 0;
            if (cp.getStatus().equals(PenalityStatus.Voided)) {
                penaltyTotal = cp.getPenaltyCount() * cp.getFees();
            }
            return penaltyTotal;
        }).mapToDouble(f -> f.doubleValue()).sum();

        return sum;
    }

    private Double getSumOfOpenCitations(List<CitationPenaltiesProjection> byMonthAndOperation) {
        double sum = byMonthAndOperation.stream().map(cp -> {
            double penaltyTotal = 0;
            if (cp.getStatus().equals(PenalityStatus.Opened)) {
                penaltyTotal = cp.getPenaltyCount() * cp.getFees();
            }
            return penaltyTotal;
        }).mapToDouble(f -> f.doubleValue()).sum();

        return sum;
    }

    private Double getSumOfCitations(List<CitationPenaltiesProjection> byMonthAndOperation) {
        double sum = byMonthAndOperation.stream().map(cp -> {
            double penaltyTotal = 0;
            Long Count = 1l;
            if (!Objects.isNull(cp.getPenaltyCount())) {
                penaltyTotal = cp.getPenaltyCount() * cp.getFees();
            } else {
                penaltyTotal = 1 * cp.getFees();
            }

            return penaltyTotal;
        }).mapToDouble(f -> f.doubleValue()).sum();
        return sum;
    }

    private Double getSumOfPaidCitations(List<CitationPenaltiesProjection> byMonthAndOperation) {

        double sum = byMonthAndOperation.stream().map(cp -> {
            double penaltyTotal = 0;
            if (cp.getStatus().equals(PenalityStatus.Settled)) {
                penaltyTotal = cp.getPenaltyCount() * cp.getFees();
            }
            return penaltyTotal;
        }).mapToDouble(f -> f.doubleValue()).sum();

        return sum;
    }

    private void sendPenaltiesEmail(List<CitationPenalties> citationPenalties, Citation citation, String operationName, String violationName, Long violationId) {
        List<OpenCitations> citations = getCitationListForAddedCitation(citation, violationName, violationId, citationPenalties);

        citationPenalties.forEach(citationPenalty -> {
            NewCitationDto newCitationDto = NewCitationDto.builder().
                    id(citation.getId()).
                    latitude(citation.getLatitude()).
                    longitude(citation.getLongitude()).
                    plateNumberEn(citation.getPlateNumberEn()).
                    operationName(operationName).
                    violationName(violationName).
                    violationId(violationId).
                    citationDate(citation.getCreatedDate()).
                    citations(citations).
                    build();

            Penalty penalty = citationPenalty.getOperationViolationPenalty().getPenalty();
            if (Objects.nonNull(penalty.getMethod()) && penalty.getMethod().equals(PenaltyMethod.EMAIL)) {
                newCitationDto.setBody(penalty.getBody());
                Email email = Email.builder().
                        toEmail(penalty.getEmail()).
                        htmlFormat(true).
                        body(newCitationEmailArabicBuilder.buildBody(newCitationDto)).
                        subject(penalty.getSubject()).build();
                try {
                    emailProducer.publishEmail(email);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<OpenCitations> getCitationListForAddedCitation(Citation citation, String violationName, Long violationId, List<CitationPenalties> citationPenalties) {
        List<OpenCitationPenaltiesDto> citationPenaltiesDtos = new LinkedList<>();
        citationPenalties.forEach(cp -> {
            Penalty penalty = cp.getOperationViolationPenalty().getPenalty();
            if (penalty.getMethod().equals(MONETARY)) {
                citationPenaltiesDtos.add(OpenCitationPenaltiesDto.builder().
                        fees(penalty.getFees()).
                        arName(penalty.getArName()).
                        enName(penalty.getEnName()).
                        build());
            }
        });

        List<OpenCitations> citations = new ArrayList<>();
        citations.add(OpenCitations.builder()
                .id(citation.getId())
                .violationName(violationName)
                .violationNameAr(violationName)
                .violationId(violationId)
                .createdDate(citation.getCreatedDate())
                .citationPenalties(citationPenaltiesDtos).build());
        return citations;
    }

    private Specification<Citation> getCitationSpecs(ViewCitationsRequest request) {
        return Specification.where(
                CitaionSpecs.citationByPlateNumberAR(request.getQuery())
                        .or(CitaionSpecs.citationByPlateNumberEN(request.getQuery()))
                        .or(CitaionSpecs.citationByNumber(request.getQuery()))
        ).and(CitaionSpecs.openCitation(request.getStatusList()));
    }

    private PageRequest getPageRequestSortedByField(ViewCitationsRequest request) {
        Set<Order> orders = new HashSet<>();

        if (Objects.isNull(request.getSortBy()) || request.getSortBy().isEmpty()) {
            orders.add(new Order(Sort.Direction.valueOf(request.getSortDirection()), CREATION_DATE));
        } else {
            for (String order : request.getSortBy()) {
                if (order.equalsIgnoreCase("enName"))
                    order = "citationPenalties.operationViolationPenalty.violation.enName";
                orders.add(new Order(Sort.Direction.valueOf(request.getSortDirection()), order));
            }
        }
        return PageRequest.of(request.getOffset(), request.getLimit(), Sort.by(new ArrayList<>(orders)));
    }


    @Override
    @Transactional
    public InvoiceDto settleCitations(String authorizationHeader, List<Long> citationIds) {
        Set<Long> settledCitationIds = new LinkedHashSet<>();
        List<InvoiceItemRequest> invoiceItemRequests = new ArrayList<>();
        List<CitationPenalties> penaltiesToInvoice = new ArrayList<>();
        List<Citation> setteledCitations = new ArrayList<>();

        citationIds.stream().forEach(id -> {

            Citation citation = this.getCitation(id);

            if (citation.getStatus().equals(CitationStatus.Settled)) {
                throw new BusinessException(Constants.ErrorKeys.CITATION_ALREADY_SETTLED, HttpStatus.NOT_ACCEPTABLE);
            }
            if (!Objects.isNull(citation)) {
                citation.setStatus(CitationStatus.Settled);
                //update Citation And it's penalties
                citation.setModifiedDate(LocalDateTime.now());
                this.citationRepository.save(citation);

                List<CitationPenalties> penalties = citation.getCitationPenalties();
                penalties.stream().forEach(cp -> {
                    if (!cp.getStatus().equals(PenalityStatus.Executed)) {
                        cp.setStatus(PenalityStatus.Settled);
                        this.citationPenaltiesService.settleCitationPenalty(cp, 1l);
                        if (cp.getOperationViolationPenalty().getPenalty().getMethod().equals(MONETARY)) {
                            penaltiesToInvoice.add(cp);
                            setteledCitations.add(citation);
                            settledCitationIds.add(id);
                        }
                    }
                });
            } else {
                throw new BusinessException(Constants.ErrorKeys.CITATION_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }

            //Prepare Calling Invoice.
            invoiceItemRequests.addAll(penaltiesToInvoice.stream()
                    .map(citationPenalties1 -> {
                        Penalty penalty = citationPenalties1.getOperationViolationPenalty().getPenalty();
                        InvoiceItemRequest quantity = InvoiceItemRequest.builder().itemName(penalty.getEnName())
                                .amount(BigDecimal.valueOf(penalty.getFees()))
                                .quantity(citationPenalties1.getActionCount().intValue()).build();
                        return quantity;
                    }).collect(Collectors.toList()));
        });
        if (CollectionUtils.isNotEmpty(invoiceItemRequests)) {

            // add feign client Call
            InvoiceRequest invoiceRequest = InvoiceRequest.builder()
                    .items(invoiceItemRequests)
                    .paymentMethod("PortalWFSettlement")
                    .source("Enforcement Portal")
                    .comment("")
                    .description("تحصيل مخالفة رقم " + settledCitationIds)
                    .build();


            ApiResponse<InvoiceDto> invoiceRes = invoiceFeignClient.addInvoice(authorizationHeader, invoiceRequest);
            Long invoiceNumber = invoiceRes.getPayload().getInvoiceNumber();
            setteledCitations.forEach(citation -> {
                citation.setInvoiceNumber(invoiceNumber);
                this.citationRepository.save(citation);
                System.out.println(citation.getCreatedDate().getMonth());
                System.out.println(citation.getCreatedDate().getMonthValue());
            });

            return invoiceRes.getPayload();
        } else {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setVoided(true);
            invoiceDto.setVoidedMsg("Void successfully");
            return invoiceDto;
        }

    }

    @Override
    public PaginationDto searchCitation(SearchCitationsRequest request) {

        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

        Long operationId = request.getOperationId();
        List<String> citationStatuses = request.getCitationStatuses();
        LocalDate fromDate = request.getFromDate();
        LocalDate toDate = request.getToDate();

        if (Objects.isNull(operationId)) {
            operationId = -1l;
        }

        if (CollectionUtils.isEmpty(citationStatuses) || citationStatuses.get(0).equalsIgnoreCase("all")) {
            citationStatuses = new LinkedList<>();
            for (CitationStatus status : CitationStatus.values()) {
                citationStatuses.add(status.toString());
            }
        }
        if (Objects.isNull(fromDate)) {
            fromDate = LocalDate.of(1970, 1, 1);
        }
        if (Objects.isNull(toDate)) {
            toDate = LocalDate.of(2200, 1, 1);
        }

        Page<CitationsSearchProjection> result = citationRepository.searchCitation(operationId, citationStatuses, fromDate, toDate, pageable);
        List<CitationsSearchDto> citationsSearchDtos = citationMapper.toCitationsSearchDto(result.getContent());

        Set<Long> shiftDetailIds = new LinkedHashSet<>();

        citationsSearchDtos.forEach(c -> {
            shiftDetailIds.add(c.getShiftWorkForceId());
        });

        if (CollectionUtils.isNotEmpty(shiftDetailIds)) {
            Map<Long, String> employeeNameMap = operationCaller.getWorkForceNamesByShiftDetailIds(Utils.getHeaderFromRequest(HttpHeaders.AUTHORIZATION).toString(), shiftDetailIds);

            citationsSearchDtos.forEach(c -> {
                c.setWorkForceName(employeeNameMap.get(c.getShiftWorkForceId()));
            });
        }

        return PaginationDto.builder()
                .content(citationsSearchDtos)
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .build();
    }
}
