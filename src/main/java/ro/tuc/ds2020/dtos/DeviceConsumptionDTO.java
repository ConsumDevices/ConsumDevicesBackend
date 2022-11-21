package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class DeviceConsumptionDTO extends RepresentationModel<DeviceConsumptionDTO> {
    private UUID id;
    @NotNull
    private int value;
    @NotNull
    private LocalDateTime date;
    //@NotNull
    //private String device;

    public DeviceConsumptionDTO(){

    }

    public DeviceConsumptionDTO(UUID id, int value, LocalDateTime date)
    {
        this.id=id;
        this.value=value;
        this.date=date;
    }

    public DeviceConsumptionDTO(int value, LocalDateTime date)
    {
        this.value=value;
        this.date=date;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DeviceConsumptionDTO deviceDTO = (DeviceConsumptionDTO) o;
        return value == deviceDTO.value
                && (date.compareTo(deviceDTO.date) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, date);
    }
}
