package messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.incoming.LoginResponse;
import messages.incoming.SystemInfoResponse;

import java.nio.charset.StandardCharsets;

public class BaseMessageType {
    @JsonIgnore
    private short messageType;

    protected BaseMessageType(short messageType) {
        this.messageType = messageType;
    }

    public short getMessageType() {
        return messageType;
    }

    public static BaseMessageType getMessageObject(byte[] incomingData, short messageType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        switch(messageType){
            case 1001:
                return objectMapper.readValue(new String(incomingData, StandardCharsets.UTF_8), LoginResponse.class);
            case 1021:
                return objectMapper.readValue(new String(incomingData, StandardCharsets.UTF_8), SystemInfoResponse.class);
        }

        throw new IllegalStateException("Unexpected message type received " + messageType);
    }
}
