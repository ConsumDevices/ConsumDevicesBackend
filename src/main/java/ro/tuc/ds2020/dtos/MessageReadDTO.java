package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class MessageReadDTO {
    @NotNull
    private String messageRead;
    @NotNull
    private UUID clientID;

    public MessageReadDTO(String messageTyping, UUID clientID) {
        this.messageRead = messageTyping;
        this.clientID = clientID;
    }

    public MessageReadDTO()
    {

    }

    public String getMessageRead() {
        return messageRead;
    }

    public void setMessageRead(String messageRead) {
        this.messageRead = messageRead;
    }

    public UUID getClientID() {
        return clientID;
    }

    public void setClientID(UUID clientID) {
        this.clientID = clientID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MessageReadDTO  messageReadDTO = (MessageReadDTO) o;
        return messageRead.equals(messageReadDTO.getMessageRead());

    }

    @Override
    public int hashCode() {
        return Objects.hash(messageRead, clientID);
    }
}
