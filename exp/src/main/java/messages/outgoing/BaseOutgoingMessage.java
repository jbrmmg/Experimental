package messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;
import messages.BaseMessageType;

public class BaseOutgoingMessage extends BaseMessageType {
    private String name;
    private String sessionId;

    protected BaseOutgoingMessage(short messageType, String name, String sessionId) {
        super(messageType);
        this.name = name;
        this.sessionId = sessionId;
    }

    @JsonGetter("Name")
    public String getName() {
        return name;
    }

    @JsonGetter("SessionID")
    public String getSessionId() {
        return sessionId;
    }
}
