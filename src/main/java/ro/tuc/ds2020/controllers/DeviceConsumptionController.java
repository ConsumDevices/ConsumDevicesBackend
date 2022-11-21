package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.services.DeviceConsumptionService;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
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

    @GetMapping(value = "/deviceName/{deviceName}")
    public ResponseEntity<List<DeviceConsumptionDTO>> getConsumptions(@PathVariable("deviceName") String deviceName) {
        //UserDetailsDTO dto = userService.findUserById(userId);
        UUID userLogatID = UserController.userLogat.getId();
        DeviceDTO device = deviceService.findByNameAndUser(deviceName, userLogatID);
        List<DeviceConsumptionDTO> dtos = deviceConsumptionService.findByDeviceName(deviceName);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
