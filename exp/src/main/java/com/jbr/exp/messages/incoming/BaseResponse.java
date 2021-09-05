package com.jbr.exp.messages.incoming;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.jbr.exp.messages.BaseMessageType;

public class BaseResponse extends BaseMessageType {
    private String name;
    private int returnCode;
    private String sessionId;

    public BaseResponse(short messageType) {
        super(messageType);
    }

    @JsonGetter("Name")
    public String getName() {
        return name;
    }

    @JsonSetter("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonGetter("Ret")
    public int getReturnCode() {
        return returnCode;
    }

    @JsonSetter("Ret")
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    @JsonGetter("SessionID")
    public String getSessionId() {
        return sessionId;
    }

    @JsonSetter("SessionID")
    public void setSessionID(String sessionId) {
        this.sessionId = sessionId;
    }
}
