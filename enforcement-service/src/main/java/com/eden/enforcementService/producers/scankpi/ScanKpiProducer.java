package com.eden.enforcementService.producers.scankpi;

import com.eden.enforcementService.producers.scankpi.dtos.ScanKpi;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScanKpiProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishScanKpi(ScanKpi scanKpi) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String citationKpiStr = mapper.writeValueAsString(scanKpi);
        this.kafkaTemplate.send(Constants.ProducerTopics.SCAN_KPI_TOPIC, citationKpiStr);
    }
}
