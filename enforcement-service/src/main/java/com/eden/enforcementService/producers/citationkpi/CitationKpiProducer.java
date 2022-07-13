package com.eden.enforcementService.producers.citationkpi;

import com.eden.enforcementService.producers.citationkpi.dtos.CitationKpi;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CitationKpiProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishCitationKpi(CitationKpi citationKpi) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String citationKpiStr = mapper.writeValueAsString(citationKpi);
        this.kafkaTemplate.send(Constants.ProducerTopics.CITATION_KPI_TOPIC, citationKpiStr);
    }
}
