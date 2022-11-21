package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceConsumptionDTO;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.DeviceConsumption;

public class DeviceConsumptionBuilder {

    private DeviceConsumptionBuilder(){

    }

    public static DeviceConsumptionDTO toDeviceConsumptionDTO(DeviceConsumption deviceConsumption) {

        return new DeviceConsumptionDTO(deviceConsumption.getId(), deviceConsumption.getValue(), deviceConsumption.getDate());
    }

    public static DeviceConsumption toDeviceConsumptionEntity(DeviceConsumptionDTO deviceConsumptionDTO, Device device) {
        return new DeviceConsumption(
                deviceConsumptionDTO.getValue(),
                deviceConsumptionDTO.getDate(),
                device
        );
    }
}
