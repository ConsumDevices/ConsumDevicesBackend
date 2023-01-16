package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class ClientMessageDTO {
    @NotNull
    private String message;
    @NotNull
    private UUID clientID;
    @NotNull
    private UUID adminID;
    @NotNull
    private String name;

    public ClientMessageDTO() {
    }

    public ClientMessageDTO(String message, UUID clientID, UUID adminID, String name) {
        this.message = message;
        this.clientID = clientID;
        this.adminID = adminID;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }

    public UUID getAdminID() {
        return adminID;
    }

    public void setAdminID(UUID adminID) {
        this.adminID = adminID;
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
        if (!super.equals(o)) return false;
        ClientMessageDTO clientMessageDTO = (ClientMessageDTO) o;
        return message.equals(clientMessageDTO.getMessage())
                && name.equals(clientMessageDTO.getName());

    }

    @Override
    public int hashCode() {
        return Objects.hash(message, name);
    }
}
