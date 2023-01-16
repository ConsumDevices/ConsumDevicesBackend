package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

public class MessageTypingDTO {
    @NotNull
    private String messageTyping;
    @NotNull
    private UUID clientID;

    public MessageTypingDTO(String messageTyping, UUID clientID) {
        this.messageTyping = messageTyping;
        this.clientID = clientID;
    }

    public MessageTypingDTO() {

    }

    public String getMessageTyping() {
        return messageTyping;
    }

    public void setMessageTyping(String messageTyping) {
        this.messageTyping = messageTyping;
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
        MessageTypingDTO  messageTypingDTO = (MessageTypingDTO) o;
        return messageTyping.equals(messageTypingDTO.getMessageTyping());

    }

    @Override
    public int hashCode() {
        return Objects.hash(messageTyping, clientID);
    }
}
