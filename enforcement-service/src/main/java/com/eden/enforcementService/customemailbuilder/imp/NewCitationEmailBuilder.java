package com.eden.enforcementService.customemailbuilder.imp;

import com.eden.enforcementService.common.dto.NewCitationDto;
import com.eden.enforcementService.customemailbuilder.CustomEmailBuilder;
import org.springframework.stereotype.Service;

@Service
public class NewCitationEmailBuilder implements CustomEmailBuilder<NewCitationDto> {


    private String emailBody = "<pre>\n" +
            "Dear,\n" +
            "\n" +
            "  Citation No#:${citationNo} is opened on plate#:${plate} " +
            "at <a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">Location</a>, area:${operationName}\n" +
            "\n" +
            "SC2 Enforcement System \n" +
            "<pre>";

    @Override
    public String buildBody(NewCitationDto newCitationDto) {
        return emailBody.replace("${plate}", newCitationDto.getPlateNumberEn())
                .replace("${citationNo}", String.valueOf(newCitationDto.getId()))
                .replace("${operationName}", newCitationDto.getOperationName())
                .replace("${lat}", String.valueOf(newCitationDto.getLatitude()))
                .replace("${lng}", String.valueOf(newCitationDto.getLongitude()));
    }



    public static void main(String[] args) {
        CustomEmailBuilder<NewCitationDto> emailBuilder = new NewCitationEmailBuilder();
        NewCitationDto newCitationDto = new NewCitationDto();
        newCitationDto.setId(3l);
        newCitationDto.setLatitude(3.221);
        newCitationDto.setLongitude(4.222);
        newCitationDto.setPlateNumberEn("palte engish");
        System.out.println(emailBuilder.buildBody(newCitationDto));
    }
}
