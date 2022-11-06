package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String address;
    @NotNull
    private float maxHourlyConsumption;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, String name, String description, String address, float maxHourlyConsumption){
        this.id=id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxHourlyConsumption = maxHourlyConsumption;
    }

    public DeviceDTO(String name, String description, String address, float maxHourlyConsumption){
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxHourlyConsumption = maxHourlyConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMaxHourlyConsumption() {
        return maxHourlyConsumption;
    }

    public void setMaxHourlyConsumption(float maxHourlyConsumption) {
        this.maxHourlyConsumption = maxHourlyConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Float.compare(deviceDTO.maxHourlyConsumption, maxHourlyConsumption) == 0
                && name.equals(deviceDTO.name)
                && description.equals(deviceDTO.description)
                && address.equals(deviceDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, address, maxHourlyConsumption);
    }
}
