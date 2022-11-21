package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.DeviceConsumptionBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.DeviceConsumption;
import ro.tuc.ds2020.repositories.DeviceConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceConsumptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceConsumptionRepository deviceConsumptionRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceConsumptionService(DeviceConsumptionRepository deviceConsumptionRepository, DeviceRepository deviceRepository) {
        this.deviceConsumptionRepository = deviceConsumptionRepository;
        this.deviceRepository = deviceRepository;
    }

    public List<DeviceConsumptionDTO> findDevicesConsumption() {
        List<DeviceConsumption> deviceList = deviceConsumptionRepository.findAll();
        return deviceList.stream()
                .map(DeviceConsumptionBuilder::toDeviceConsumptionDTO)
                .collect(Collectors.toList());
    }

    public DeviceConsumptionDTO findDeviceConsumptionById(UUID id) {
        Optional<DeviceConsumption> deviceConsumptionOptional = deviceConsumptionRepository.findById(id);
        if (!deviceConsumptionOptional.isPresent()) {
            LOGGER.error("DeviceConsumption with id {} was not found in db", id);
            throw new ResourceNotFoundException(DeviceConsumption.class.getSimpleName() + " with id: " + id);
        }
        return DeviceConsumptionBuilder.toDeviceConsumptionDTO(deviceConsumptionOptional.get());
    }

    public List<DeviceConsumptionDTO> findByDeviceName(String deviceName) {
        Optional<Device> deviceOptional = deviceRepository.findByName(deviceName);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with name {} was not found in db", deviceName);
            throw new ResourceNotFoundException(DeviceConsumption.class.getSimpleName() + " with name: " + deviceName);
        }

        UUID deviceId = deviceOptional.get().getId();

        List<DeviceConsumption> deviceList = deviceConsumptionRepository.findByDeviceId(deviceId);
        return deviceList.stream()
                .map(DeviceConsumptionBuilder::toDeviceConsumptionDTO)
                .collect(Collectors.toList());
    }

}
