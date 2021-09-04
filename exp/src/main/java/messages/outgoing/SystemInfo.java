package messages.outgoing;

public class SystemInfo extends BaseOutgoingMessage {
    public SystemInfo(String sessionId) {
        super((short)1020,"SystemInfo",sessionId);
    }
}
