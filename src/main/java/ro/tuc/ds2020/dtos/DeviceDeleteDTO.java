package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class DeviceDeleteDTO {
    @NotNull
    private String name;

    public DeviceDeleteDTO()
    {

    }

    public DeviceDeleteDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDeleteDTO that = (DeviceDeleteDTO) o;
        return  Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
