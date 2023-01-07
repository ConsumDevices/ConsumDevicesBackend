package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceDeleteDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.dtos.UserLoginDTO;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value="/clientD/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesClient(@PathVariable("id") UUID userId) {
        //List<DeviceDTO> dtos = deviceService.findDevicesClient(UserController.userLogat.getId());

        //aici probabil trebuie sa folosim ceva din frontend, nu user logat
        List<DeviceDTO> dtos = deviceService.findDevicesClient(userId);

        System.out.println(dtos.size());
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        UUID deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @PostMapping(value="/update")
    public ResponseEntity<UUID> updateDevice(@Valid @RequestBody DeviceDTO deviceDTO) {

        DeviceDTO dto = deviceService.findByName(deviceDTO.getName());
        dto.setAddress(deviceDTO.getAddress());
        dto.setDescription(deviceDTO.getDescription());
        dto.setMaxHourlyConsumption(deviceDTO.getMaxHourlyConsumption());
        dto.setUsername(deviceDTO.getUsername());

        UUID userID = deviceService.update(dto);
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @PostMapping(value="/delete")
    public ResponseEntity<UUID> deleteDevice(@Valid @RequestBody DeviceDeleteDTO deviceDeleteDTO) {

        DeviceDTO dto = deviceService.findByName(deviceDeleteDTO.getName());

        //System.out.println("Controller " + userDetailsDTO.getId());

        UUID deviceId = deviceService.delete(dto.getId());
        return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") UUID deviceId) {
        DeviceDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //TODO: UPDATE, DELETE per resource

}
