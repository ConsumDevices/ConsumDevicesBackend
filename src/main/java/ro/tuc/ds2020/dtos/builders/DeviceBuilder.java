package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

public class DeviceBuilder {

    private DeviceBuilder() {
    }

    public static DeviceDTO toDeviceDTO(Device device) {
        if(device.getUser() == null)
        {
            return new DeviceDTO(device.getId(), device.getName(), device.getDescription(), device.getAddress(),device.getMaxHourlyConsumption(), "");
        }
        else {
            return new DeviceDTO(device.getId(), device.getName(), device.getDescription(), device.getAddress(), device.getMaxHourlyConsumption(), device.getUser().getName());
        }
    }

    public static Device toDeviceEntity(DeviceDTO deviceDTO, User user) {
        return new Device(deviceDTO.getName(),
                deviceDTO.getDescription(),
                deviceDTO.getAddress(),
                deviceDTO.getMaxHourlyConsumption(),
                user);
    }

    public static Device toDeviceEntityUpdate(DeviceDTO deviceDTO, User user) {
        return new Device(deviceDTO.getId(),
                deviceDTO.getName(),
                deviceDTO.getDescription(),
                deviceDTO.getAddress(),
                deviceDTO.getMaxHourlyConsumption(),
                user);
    }
}
