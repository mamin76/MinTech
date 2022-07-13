package com.eden.enforcementService.producers.email;

import com.eden.enforcementService.producers.email.dtos.Email;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishEmail(Email email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String emailsStr = mapper.writeValueAsString(email);
        this.kafkaTemplate.send(Constants.ProducerTopics.EMAIL_TOPIC, emailsStr);
    }

}
