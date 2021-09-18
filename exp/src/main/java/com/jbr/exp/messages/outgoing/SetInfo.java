package com.jbr.exp.messages.outgoing;

import com.fasterxml.jackson.annotation.JsonGetter;

public class SetInfo extends BaseOutgoingMessage {
    private final String data;

    public SetInfo(String sessionId, String name, String data) {
        super((short)1040,"SystemInfo",sessionId);

        this.data = data;
    }

    @JsonGetter("data")
    public String getData() {
        return data;
    }
}
