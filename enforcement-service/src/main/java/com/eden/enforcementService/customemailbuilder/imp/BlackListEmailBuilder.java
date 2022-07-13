package com.eden.enforcementService.customemailbuilder.imp;

import com.eden.enforcementService.common.dto.BlackListMailDto;
import com.eden.enforcementService.customemailbuilder.CustomEmailBuilder;
import org.springframework.stereotype.Service;

@Service
public class BlackListEmailBuilder implements CustomEmailBuilder<BlackListMailDto> {


    private String emailBody =
            "Dear,\n" +
            "\n" +
            "  BlackListed vehicle plate#:${plate} , country:${countryName} founded  " +
            "at <a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">Location</a>, street:${streetName}, area:${operationName}\n" +
            "\n" +
            "SC2 Enforcement System \n" ;

    @Override
    public String buildBody(BlackListMailDto blackListMailDto) {
        String body = blackListMailDto.getBody()
                .replace("${plate}", blackListMailDto.getPlateNumberEn())
                .replace("${operationName}", blackListMailDto.getOperationName())
                .replace("${streetName}", blackListMailDto.getStreetName())
                .replace("${countryName}", blackListMailDto.getCountryName())
                .replace("${lat}", String.valueOf(blackListMailDto.getLatitude()))
                .replace("${lng}", String.valueOf(blackListMailDto.getLongitude()));
        StringBuilder builder = new StringBuilder("<pre>\n").append(body).append("\n<pre>");
        return builder.toString();
    }


    public static void main(String[] args) {
        CustomEmailBuilder<BlackListMailDto> emailBuilder = new BlackListEmailBuilder();
        BlackListMailDto newCitationDto = new BlackListMailDto();
        newCitationDto.setId(3l);
        newCitationDto.setLatitude(3.221);
        newCitationDto.setLongitude(4.222);
        newCitationDto.setPlateNumberEn("palte engish");
        newCitationDto.setBody(((BlackListEmailBuilder)emailBuilder).emailBody);
        newCitationDto.setStreetName("streeeeet");
        newCitationDto.setCountryName("country name");
        newCitationDto.setOperationName("operrrrrr");
        System.out.println(emailBuilder.buildBody(newCitationDto));
    }
}
