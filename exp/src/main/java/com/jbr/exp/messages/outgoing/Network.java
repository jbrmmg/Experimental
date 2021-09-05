package com.jbr.exp.messages.outgoing;

public class Network extends BaseOutgoingMessage {
    public Network(String sessionId) {
        super((short)1042,"NetWork.NetCommon",sessionId);
    }
}
