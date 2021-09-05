package com.jbr.exp.messages.incoming;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse extends BaseResponse {
    private int aliveInterval;
    private int channelNumber;
    private String deviceType;
    private int extraChannel;

    public LoginResponse() {
        super((short)1001);
    }

    @JsonGetter("AliveInterval")
    public int getAliveInterval() {
        return aliveInterval;
    }

    @JsonSetter("AliveInterval")
    public void setAliveInterval(int aliveInterval) {
        this.aliveInterval = aliveInterval;
    }

    @JsonGetter("ChannelNum")
    public int getChannelNumber() {
        return channelNumber;
    }

    @JsonSetter("ChannelNum")
    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }

    @JsonGetter("DeviceType ")
    public String getDeviceType() {
        return deviceType;
    }

    @JsonSetter("DeviceType ")
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    @JsonGetter("ExtraChannel")
    public int getExtraChannel() {
        return extraChannel;
    }

    @JsonSetter("ExtraChannel")
    public void setExtraChannel(int extraChannel) {
        this.extraChannel = extraChannel;
    }

    @Override
    public String toString() {
        return "Return Code:" + this.getReturnCode() + " " + this.getSessionId();
    }
}
