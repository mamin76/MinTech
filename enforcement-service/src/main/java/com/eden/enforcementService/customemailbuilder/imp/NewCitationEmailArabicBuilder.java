package com.eden.enforcementService.customemailbuilder.imp;

import com.eden.enforcementService.common.dto.NewCitationDto;
import com.eden.enforcementService.common.dto.OpenCitationPenaltiesDto;
import com.eden.enforcementService.customemailbuilder.CustomEmailBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class NewCitationEmailArabicBuilder implements CustomEmailBuilder<NewCitationDto> {


//    private String emailBody = "<pre>\n" +
//            "Dear,\n" +
//            "\n" +
//            "  Citation No#:${citationNo} is opened on plate#:${plate} " +
//            "at <a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">Location</a>, area:${operationName}\n" +
//            "\n" +
//            "SC2 Enforcement System \n" +
//            "<pre>";

    private String emailBody = "<head>\n" +
            "    <style>\n" +
            "        pre{text-align: right;}\n" +
            "    </style>\n" +
            "</head>" +
            "<pre> \n" +
            " ${db_body} \n\n" +
            "يرجى ملاحظة أنه تم رصد المركبة التي تحمل" +
            "\n${plate} : لوحة رقم" +
            "\nبالموقع:<a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">الموقع</a>" +
            "\n${operationName} : بمنطقة" +
            "\n" +
            "\n:المخالفات المسجلة على المركبة\n" +
            " ${citations_info}" +
            "SC2 Enforcement System<pre>";


    @Override
    public String buildBody(NewCitationDto newCitationDto) {
        return emailBody.replace("${plate}", newCitationDto.getPlateNumberEn())
                .replace("${operationName}", newCitationDto.getOperationName())
                .replace("${db_body}", newCitationDto.getBody())
                .replace("${citations_info}", buildCitationsInfo(newCitationDto))
                .replace("${lat}", String.valueOf(newCitationDto.getLatitude()))
                .replace("${lng}", String.valueOf(newCitationDto.getLongitude()));
    }

    private String buildCitationsInfo(NewCitationDto newCitationDto) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < newCitationDto.getCitations().size(); i++) {
            result.append(buildCitationInfo(String.valueOf(newCitationDto.getCitations().get(i).getId()),
                    String.valueOf(newCitationDto.getCitations().get(i).getViolationId()),
                    newCitationDto.getCitations().get(i).getViolationNameAr(),
                    newCitationDto.getCitations().get(i).getCreatedDate(), newCitationDto.getCitations().get(i).getCitationPenalties()));
        }
        return result.toString();
    }

    private String buildCitationInfo(String citationNumber, String violationNumber, String violationName, LocalDateTime createdDate, List<OpenCitationPenaltiesDto> citationPenalties) {
        return new StringBuilder("رقم المخالفة: ").append(citationNumber).append("\n").
                append("كود المخالفة: ").append(violationNumber).append("\n").
                append("نوع المخالفة: ").append(violationName).append("\n").
                append("التاريخ والوقت: ").append(createdDate.format(DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss",new Locale("ar")))).append("\n").
                append(buildPenaltiesInfo(citationPenalties)).append("\n").toString();
    }

    private String buildPenaltiesInfo(List<OpenCitationPenaltiesDto> citationPenalties) {

        StringBuilder penaltiesInfo = new StringBuilder("");
        for (int i = 0; i < citationPenalties.size(); i++) {
            penaltiesInfo.append(buildPenaltyInfo(citationPenalties.get(i).getArName(), String.valueOf(citationPenalties.get(i).getFees()))).append("\n");
        }
        return penaltiesInfo.toString();
    }

    private String buildPenaltyInfo(String name, String fees) {
        return new StringBuilder("\n").append("قيمة المخالفة: ").append(fees).append(" ").append("ريال").append("\n")
                .append("الاجراء: ").append(name)
                .toString();
    }



    public static void main(String[] args) {
        CustomEmailBuilder<NewCitationDto> emailBuilder = new NewCitationEmailArabicBuilder();
        NewCitationDto newCitationDto = new NewCitationDto();
        newCitationDto.setId(3l);
        newCitationDto.setLatitude(3.221);
        newCitationDto.setLongitude(4.222);
        newCitationDto.setPlateNumberEn("palte engish");
        System.out.println(emailBuilder.buildBody(newCitationDto));
    }
}
