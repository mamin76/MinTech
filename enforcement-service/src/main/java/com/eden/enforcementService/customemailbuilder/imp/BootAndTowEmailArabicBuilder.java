package com.eden.enforcementService.customemailbuilder.imp;

import com.eden.enforcementService.common.dto.OpenCitationDto;
import com.eden.enforcementService.common.dto.OpenCitationPenaltiesDto;
import com.eden.enforcementService.common.dto.OpenCitations;
import com.eden.enforcementService.customemailbuilder.CustomEmailBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class BootAndTowEmailArabicBuilder implements CustomEmailBuilder<OpenCitationDto> {


    private String emailBody = "<head>\n" +
            "    <style>\n" +
            "        pre{text-align: right;}\n" +
            "    </style>\n" +
            "</head>" +
            "" +
            "<pre> يرجى ملاحظة أنه تم رصد المركبة التي تحمل" +
            "\n${plate} : لوحة رقم" +
            "\nبالموقع:<a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">الموقع</a>" +
            "\n${operationName} : بمنطقة" +
            "\n" +
            "\n:المخالفات المسجلة على المركبة\n" +
            " ${citations_info}" +
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
            result.append(buildCitationInfo(String.valueOf(openCitationDto.getCitations().get(i).getId()),
                    String.valueOf(openCitationDto.getCitations().get(i).getViolationId()),
                    openCitationDto.getCitations().get(i).getViolationNameAr(),
                    openCitationDto.getCitations().get(i).getCreatedDate(), openCitationDto.getCitations().get(i).getCitationPenalties()));
        }
        return result.toString();
    }

    private String buildCitationInfo(String citationNumber,String violationNumber, String violationName, LocalDateTime createdDate, List<OpenCitationPenaltiesDto> citationPenalties) {
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
        CustomEmailBuilder<OpenCitationDto> emailBuilder = new BootAndTowEmailArabicBuilder();
        OpenCitationDto openCitationDto = new OpenCitationDto();
        openCitationDto.setAddress("Address");
        openCitationDto.setLatitude(3.3);
        openCitationDto.setLongitude(4.3);
        openCitationDto.setPlateNumberEn("hfhg");
        openCitationDto.setOperationName("abd");
        List<OpenCitationPenaltiesDto> citationPenalties = new ArrayList<>();
        citationPenalties.add(OpenCitationPenaltiesDto.builder().enName("aa").arName("كلبشة").fees(2.3).build());
        citationPenalties.add(OpenCitationPenaltiesDto.builder().enName("aa").arName("كلبشة").fees(2.3).build());
        OpenCitations e = OpenCitations.builder().createdDate(LocalDateTime.now()).violationNameAr("الوقوف لفترة تفوق مدة الحجز").id(1234l).violationId(888888l).citationPenalties(citationPenalties).build();
        openCitationDto.getCitations().add(e);
        System.out.println(emailBuilder.buildBody(openCitationDto));
    }
}
