package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.SensorValuesDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceConsumptionService;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin
@RequestMapping(value = "/deviceConsumption")
public class DeviceConsumptionController {

    private final DeviceConsumptionService deviceConsumptionService;
    private final DeviceService deviceService;

    public static ArrayList<SensorValuesDTO> sensorValuesList = new ArrayList<>();

    @Autowired
    SimpMessagingTemplate webSocketMessage;

    @Autowired
    public DeviceConsumptionController(DeviceConsumptionService deviceConsumptionService, DeviceService deviceService) {
        this.deviceConsumptionService = deviceConsumptionService;
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceConsumptionDTO>> getDevicesConsumption() {
        List<DeviceConsumptionDTO> dtos = deviceConsumptionService.findDevicesConsumption();
        for (DeviceConsumptionDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceConsumptionController.class)
                    .getDeviceConsumption(dto.getId())).withRel("deviceConsumptionDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceConsumptionDTO> getDeviceConsumption(@PathVariable("id") UUID deviceConsumptionId) {
        DeviceConsumptionDTO dto = deviceConsumptionService.findDeviceConsumptionById(deviceConsumptionId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/deviceName/{deviceName}/{id}")
    public ResponseEntity<List<DeviceConsumptionDTO>> getConsumptions(@PathVariable("deviceName") String deviceName, @PathVariable("id") UUID userId) {
        //UserDetailsDTO dto = userService.findUserById(userId);
        //UUID userLogatID = UserController.userLogat.getId();

        //si aici folosim din frontend, nu user logat
        UUID userLogatID = userId;



        DeviceDTO device = deviceService.findByNameAndUser(deviceName, userLogatID);
        List<DeviceConsumptionDTO> dtos = deviceConsumptionService.findByDeviceName(deviceName);
        Collections.sort(dtos);
        return new ResponseEntity<>(dtos, HttpStatus.OK);

    }


    //@Scheduled(cron = "*/10 * * ? * *")
    @Scheduled(cron = "0 * * * * *")
    public void processSensorValues() {
        //trimit din cealalta aplicatie o data la 10 secunde si aici primesc o data pe minut in loc
        //sa trimit o data la 10 minute si sa primesc o data pe ora
        for(SensorValuesDTO sensorValue : sensorValuesList)
        {
            System.out.println(sensorValue.toString());
        }

        //grupez datele in functie de timp, adica datele din 10 in 10 secunde le grupez la minutul anume
        //trebuie sa fac sume la valorile din acelasi minut
        if(sensorValuesList.size()>0)
        {
            LocalDateTime dataActuala = sensorValuesList.get(0).getDate().truncatedTo(ChronoUnit.MINUTES);
            //System.out.println(dataActuala);
            float suma = 0.0f;
            UUID deviceIdActual = sensorValuesList.get(0).getDeviceId();
            for(SensorValuesDTO sensorValue : sensorValuesList)
            {
                if(sensorValue.getDate().getMinute() == dataActuala.getMinute())
                {
                    suma = suma + sensorValue.getValue();
                }
                else
                {
                    //am terminat elementele din coada care trebuie grupate
                    SensorValuesDTO sensorValueActual = new SensorValuesDTO();
                    sensorValueActual.setValue(suma);
                    sensorValueActual.setDate(dataActuala);
                    sensorValueActual.setDeviceId(sensorValue.getDeviceId());
                    deviceConsumptionService.insert(sensorValueActual);

                    //aici fac si testarea daca s-a depasit limita
                    DeviceDTO deviceActual = deviceService.findDeviceById(sensorValueActual.getDeviceId());
                    float maxHourlyConsumption = deviceActual.getMaxHourlyConsumption();
                    if(Float.compare(maxHourlyConsumption, sensorValueActual.getValue())<0)
                    {
                        //obtin lista device-urilor userului logat ca sa verific daca acest device este al lui

                        //List<DeviceDTO> dtos = deviceService.findDevicesClient(UserController.userLogat.getId());
                        List<DeviceDTO> dtos = deviceService.findDevices();


                        //aici din nou nu mai folosim user logat de aici, ci din frontend


                        for(DeviceDTO device: dtos)
                        {
                            if(device.getId().equals(sensorValueActual.getDeviceId()))
                            {
                                //s-a depasit limita de consum per ora
                                System.out.println("S-a depasit limita!!!" + " maxim:" + maxHourlyConsumption + " actual:" + sensorValueActual.getValue());
                                webSocketMessage.convertAndSend("/wsnotification/message", sensorValueActual);
                            }
                        }
                    }




                    suma = sensorValue.getValue();
                    dataActuala = sensorValue.getDate();
                    deviceIdActual = sensorValue.getDeviceId();
                    //System.out.println(dataActuala);

                    //System.out.println(sensorValueActual.toString());

                }
            }
            //cand ies din for trebuie sa fac ultimul insert
            SensorValuesDTO sensorValueActual = new SensorValuesDTO();
            sensorValueActual.setValue(suma);
            sensorValueActual.setDate(dataActuala);
            sensorValueActual.setDeviceId(deviceIdActual);
            deviceConsumptionService.insert(sensorValueActual);

            //System.out.println(sensorValueActual.toString());

            //aici fac si testarea daca s-a depasit limita
            DeviceDTO deviceActual = deviceService.findDeviceById(sensorValueActual.getDeviceId());
            float maxHourlyConsumption = deviceActual.getMaxHourlyConsumption();
            if(Float.compare(maxHourlyConsumption, sensorValueActual.getValue())<0)
            {
                //obtin lista device-urilor userului logat ca sa verific daca acest device este al lui
                //List<DeviceDTO> dtos = deviceService.findDevicesClient(UserController.userLogat.getId());
                List<DeviceDTO> dtos = deviceService.findDevices();


                //aici din nou nu mai folosim user logat de aici, ci din frontend


                for(DeviceDTO device: dtos)
                {
                    if(device.getId().equals(sensorValueActual.getDeviceId()))
                    {
                        //s-a depasit limita de consum per ora
                        System.out.println("S-a depasit limita!!!" + " maxim:" + maxHourlyConsumption + " actual:" + sensorValueActual.getValue());
                        webSocketMessage.convertAndSend("/wsnotification/message", sensorValueActual);
                    }
                }
            }

            sensorValuesList.clear();

        }
    }
}
