package ro.tuc.ds2020.sensorReader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.controllers.DeviceConsumptionController;
import ro.tuc.ds2020.dtos.SensorValuesDTO;

import java.util.HashMap;
import java.util.Map;

@Component
public class SensorReader {
    private static final Logger log = LoggerFactory.getLogger(SensorReader.class);


    @RabbitListener(queues = "queueValues.queue")
    public void listen(String payloadSensorValue) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        SensorValuesDTO sensorValuesDTO = objectMapper.readValue(payloadSensorValue, SensorValuesDTO.class);

        log.info("Obiectul citit din aplicatie " + payloadSensorValue);

        DeviceConsumptionController.sensorValuesList.add(sensorValuesDTO);
    }
}
