package messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;
import messages.BaseMessageType;

public class SystemInfo extends BaseMessageType {
    private String name;
    private String sessionId;

    public SystemInfo(String sessionId) {
        super((short)1020);
        this.name = "SystemInfo";
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
