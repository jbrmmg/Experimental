package messages.incoming;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import messages.BaseMessageType;

public class LoginResponse extends BaseMessageType {
    private int aliveInterval;
    private int channelNumber;
    private String deviceType;
    private int extraChannel;
    private int returnCode;
    private String sessionId;

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

    @JsonGetter("Ret")
    public int getReturnCode() {
        return returnCode;
    }

    @JsonSetter("Ret")
    public void setRet(int returnCode) {
        this.returnCode = returnCode;
    }

    @JsonGetter("SessionID")
    public String getSessionId() {
        return sessionId;
    }

    @JsonSetter("SessionID")
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "Return Code:" + this.returnCode + " " + this.sessionId;
    }
}
