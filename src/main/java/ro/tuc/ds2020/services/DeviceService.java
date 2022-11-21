package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> findDevicesClient(UUID userId) {
        //Optional<User> userOptional = deviceRepository.findById(id);
        List<Device> deviceList = deviceRepository.findAllForClient(userId);

        //if(deviceList.isEmpty())
        //{
        //    LOGGER.error("There are no devices for the user with id {}", userId);
        //    throw new ResourceNotFoundException("User with id: " + userId);
        //}

        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO findDeviceById(UUID id) {
        Optional<Device> deviceOptional = deviceRepository.findById(id);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDTO(deviceOptional.get());
    }

    public UUID insert(DeviceDTO deviceDTO) {

        Optional<User> user = userRepository.findByName(deviceDTO.getUsername());
        User userDeAdaugat;
        //System.out.println(deviceDTO.getName() + " " + deviceDTO.getUsername() + "  " + users.size());
        if (!user.isPresent()) {
            userDeAdaugat = null;
        }
        else
        {
            userDeAdaugat = user.get();
        }

        Device device = DeviceBuilder.toDeviceEntity(deviceDTO, userDeAdaugat);
        //System.out.println(userDeAdaugat.getName());
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public UUID update(DeviceDTO deviceDTO) {

        Optional<User> user = userRepository.findByName(deviceDTO.getUsername());
        User userDeAdaugat;
        if (!user.isPresent()) {
            userDeAdaugat = null;
        }
        else
        {
            userDeAdaugat = user.get();
        }

        Device device = DeviceBuilder.toDeviceEntityUpdate(deviceDTO, userDeAdaugat);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was update in db", device.getId());
        return device.getId();
    }

    public UUID delete(UUID deviceId) {
        deviceRepository.deleteById(deviceId);
        LOGGER.debug("Device with id {} was deleted from db", deviceId);
        return deviceId;
    }

    public DeviceDTO findByName(String name) {
        Optional<Device> deviceOptional = deviceRepository.findByName(name);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with name {} was not found in db", name);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with name: " + name);
        }
        return DeviceBuilder.toDeviceDTO(deviceOptional.get());
    }

    public DeviceDTO findByNameAndUser(String name, UUID userId) {
        Optional<Device> deviceOptional = deviceRepository.findByNameAndUserID(name, userId);
        if (!deviceOptional.isPresent()) {
            LOGGER.error("Device with name {} was not found in db", name);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with name: " + name);
        }
        return DeviceBuilder.toDeviceDTO(deviceOptional.get());
    }

}
