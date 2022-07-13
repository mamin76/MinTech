package com.eden.enforcementService.customemailbuilder.imp;

import com.eden.enforcementService.common.dto.OpenCitationDto;
import com.eden.enforcementService.common.dto.OpenCitationPenaltiesDto;
import com.eden.enforcementService.common.dto.OpenCitations;
import com.eden.enforcementService.customemailbuilder.CustomEmailBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BootAndTowEmailBuilder implements CustomEmailBuilder<OpenCitationDto> {


    private String emailBody = "<pre>Dears,\n" +
            "\n" +
            "Kindly be noted that vehicle with plate number: ${plate} is currently at <a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">Location</a> , area:${operationName} " +
            " and has unsettled citations as below:\n" +
            " ${citations_info} \n" +
            "SC2 Enforcement System<pre>";

    @Override
    public String buildBody(OpenCitationDto openCitationDto) {
        return emailBody.replace("${plate}", openCitationDto.getPlateNumberEn())
                .replace("${operationName}", openCitationDto.getOperationName())
                .replace("${citations_info}", buildCitationsInfo(openCitationDto))
                .replace("${lat}", String.valueOf(openCitationDto.getLatitude()))
                .replace("${lng}", String.valueOf(openCitationDto.getLongitude()));
    }

    private String buildCitationsInfo(OpenCitationDto openCitationDto) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < openCitationDto.getCitations().size(); i++) {
            result.append((i+1) + "- " + buildCitationInfo(String.valueOf(openCitationDto.getCitations().get(i).getId()),
                    openCitationDto.getCitations().get(i).getViolationName(),
                    openCitationDto.getCitations().get(i).getCreatedDate(), openCitationDto.getCitations().get(i).getCitationPenalties()));
        }
        return result.toString();
    }

    private String buildCitationInfo(String citationNumber, String violationName, LocalDateTime createdDate, List<OpenCitationPenaltiesDto> citationPenalties) {
        return new StringBuilder("Citation Number: ").append(citationNumber).append(",").
                append("\tViolation Name: ").append(violationName).append(",").
                append("\t on: ").append(createdDate).append(buildPenaltiesInfo(citationPenalties)).append("\n").toString();
    }

    private String buildPenaltiesInfo(List<OpenCitationPenaltiesDto> citationPenalties) {

        StringBuilder penaltiesInfo = new StringBuilder("\n\tPenalties: \n").append(String.format("%-100s  \t%-20s", "\t\tAction Name", "Fees"));
        for (int i = 0; i < citationPenalties.size(); i++) {
            penaltiesInfo.append(buildPenaltyInfo(citationPenalties.get(i).getEnName(), String.valueOf(citationPenalties.get(i).getFees()))).append("\n");
        }
        return penaltiesInfo.toString();
    }

    private String buildPenaltyInfo(String name, String fees) {
        return new StringBuilder("\n").append(String.format("\t\t%-100s  \t%-20s", name, fees)).toString();
    }

    public static void main(String[] args) {
        CustomEmailBuilder<OpenCitationDto> emailBuilder = new BootAndTowEmailBuilder();
        OpenCitationDto openCitationDto = new OpenCitationDto();
        openCitationDto.setAddress("Address");
        openCitationDto.setLatitude(3.3);
        openCitationDto.setLongitude(4.3);
        openCitationDto.setPlateNumberEn("palte number");
        List<OpenCitationPenaltiesDto> citationPenalties = new ArrayList<>();
        citationPenalties.add(OpenCitationPenaltiesDto.builder().enName("aa").fees(2.3).build());
        OpenCitations e=OpenCitations.builder().violationName("VVV").citationPenalties(citationPenalties).build();
        openCitationDto.getCitations().add(e);
        System.out.println(emailBuilder.buildBody(openCitationDto));
    }
}
