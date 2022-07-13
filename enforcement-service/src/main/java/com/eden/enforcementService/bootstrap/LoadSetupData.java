package com.eden.enforcementService.bootstrap;

import com.eden.enforcementService.common.model.entity.Penalty;
import com.eden.enforcementService.common.model.enums.PenaltyMethod;
import com.eden.enforcementService.common.model.enums.PenaltyType;
import com.eden.enforcementService.config.Properties;
import com.eden.enforcementService.repository.PenaltyRepository;
import com.eden.enforcementService.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@Transactional
public class LoadSetupData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private PenaltyRepository penaltyRepository;

    @Autowired
    private Properties properties;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        addBlackListPenalty();
    }

    private void addBlackListPenalty() {
        Penalty penalty = penaltyRepository.findByEnName(Constants.enName);
        if (penalty == null) {
            penalty = new Penalty();
        }
//        String body = "Dear,\n" +
//                "\n" +
//                "  BlackListed vehicle plate#:${plate} , country:${countryName} founded  at <a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">Location</a>, street:${streetName}, area:${operationName}\n" +
//                "\n" +
//                "SC2 Enforcement System ";

        String body = "<head>\n" +
                "    <style>\n" +
                "        pre{text-align: right;}\n" +
                "    </style>\n" +
                "</head>" +
                "" +
                "<pre> يرجى ملاحظة أنه تم رصد مركبة مسجلة بالقائمة السوداء تحمل" +
                "\n${plate} : لوحة رقم" +
                "\nبالموقع:<a href=\"https://www.google.com/maps/search/?api=1&query=${lat},${lng}\">الموقع</a>" +
                "\n${operationName} : بمنطقة" +
                "\n" +
                " الاجراء : ابلاغ الجهات المختصة" +
                "\n" +
                "SC2 Enforcement System<pre>";


        penalty.setArName(Constants.arName);
        penalty.setEnName(Constants.enName);
        penalty.setEmail(properties.getEnforcementTeamMail());
        penalty.setEnDescription("Fixed email to all black list");
        penalty.setArDescription("Fixed email to all black list");
        penalty.setSubject("مركبة مدرجة في القائمة السوداء");
        penalty.setBody(body);
        penalty.setFees(0.0);
        penalty.setMethod(PenaltyMethod.EMAIL);
        penalty.setType(PenaltyType.ONE_PRICE);

        penaltyRepository.save(penalty);

    }

}
