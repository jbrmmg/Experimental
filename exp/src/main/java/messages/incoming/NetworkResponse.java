package messages.incoming;

import messages.BaseMessageType;

public class NetworkResponse extends BaseMessageType {
    public NetworkResponse() {
        super((short)1043);
    }
}
