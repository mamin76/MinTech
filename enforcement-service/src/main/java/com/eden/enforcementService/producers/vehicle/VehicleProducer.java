package com.eden.enforcementService.producers.vehicle;

import com.eden.enforcementService.producers.vehicle.dtos.VehicleDto;
import com.eden.enforcementService.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VehicleProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishNewVehicle(VehicleDto vehicleDto) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String vehicleStr = mapper.writeValueAsString(vehicleDto);
        this.kafkaTemplate.send(Constants.ProducerTopics.VEHICLE_CREATED_TOPIC, vehicleStr);
    }

}
