package messages.incoming;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import messages.BaseMessageType;

public class SystemInfoResponse extends BaseMessageType {
    public class SystemInfoData {
        private int alarmInChannel;
        private int alarmOutChannel;
        private int audioInChannel;
        private String buildTime;
        private int combineSwitch;
        private String deviceRunTime;
        private int digChannel;
        private String encryptVersion;
        private int extraChannel;
        private String hardware;
        private String hardwareVersion;
        private String serialNumber;
        private String softwareVersion;
        private int talkInChannel;
        private int talkOutChannel;
        private String updateTime;
        private String updateType;
        private int videoInChannel;
        private int videoOutChannel;

        @JsonGetter("AlarmInChannel")
        public int getAlarmInChannel() {
            return alarmInChannel;
        }

        @JsonSetter("AlarmInChannel")
        public void setAlarmInChannel(int alarmInChannel) {
            this.alarmInChannel = alarmInChannel;
        }

        @JsonGetter("AlarmOutChannel")
        public int getAlarmOutChannel() {
            return alarmOutChannel;
        }

        @JsonSetter("AlarmOutChannel")
        public void setAlarmOutChannel(int alarmOutChannel) {
            this.alarmOutChannel = alarmOutChannel;
        }

        @JsonGetter("AudioInChannel")
        public int getAudioInChannel() {
            return audioInChannel;
        }

        @JsonSetter("AudioInChannel")
        public void setAudioInChannel(int audioInChannel) {
            this.audioInChannel = audioInChannel;
        }

        @JsonGetter("BuildTime")
        public String getBuildTime() {
            return buildTime;
        }

        @JsonSetter("BuildTime")
        public void setBuildTime(String buildTime) {
            this.buildTime = buildTime;
        }

        @JsonGetter("CombineSwitch")
        public int getCombineSwitch() {
            return combineSwitch;
        }

        @JsonSetter("CombineSwitch")
        public void setCombineSwitch(int combineSwitch) {
            this.combineSwitch = combineSwitch;
        }

        @JsonGetter("DeviceRunTime")
        public String getDeviceRunTime() {
            return deviceRunTime;
        }

        @JsonGetter("DeviceRunTime")
        public void setDeviceRunTime(String deviceRunTime) {
            this.deviceRunTime = deviceRunTime;
        }

        @JsonGetter("DigChannel")
        public int getDigChannel() {
            return digChannel;
        }

        @JsonSetter("DigChannel")
        public void setDigChannel(int digChannel) {
            this.digChannel = digChannel;
        }

        @JsonGetter("EncryptVersion")
        public String getEncryptVersion() {
            return encryptVersion;
        }

        @JsonSetter("EncryptVersion")
        public void setEncryptVersion(String encryptVersion) {
            this.encryptVersion = encryptVersion;
        }

        @JsonGetter("ExtraChannel")
        public int getExtraChannel() {
            return extraChannel;
        }

        @JsonSetter("ExtraChannel")
        public void setExtraChannel(int extraChannel) {
            this.extraChannel = extraChannel;
        }

        @JsonGetter("HardWare")
        public String getHardware() {
            return hardware;
        }

        @JsonSetter("HardWare")
        public void setHardware(String hardware) {
            this.hardware = hardware;
        }

        @JsonGetter("HardWareVersion")
        public String getHardwareVersion() {
            return hardwareVersion;
        }

        @JsonSetter("HardWareVersion")
        public void setHardwareVersion(String hardwareVersion) {
            this.hardwareVersion = hardwareVersion;
        }

        @JsonGetter("SerialNo")
        public String getSerialNumber() {
            return serialNumber;
        }

        @JsonSetter("SerialNo")
        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        @JsonGetter("SoftWareVersion")
        public String getSoftwareVersion() {
            return softwareVersion;
        }

        @JsonSetter("SoftWareVersion")
        public void setSoftwareVersion(String softwareVersion) {
            this.softwareVersion = softwareVersion;
        }

        @JsonGetter("TalkInChannel")
        public int getTalkInChannel() {
            return talkInChannel;
        }

        @JsonSetter("TalkInChannel")
        public void setTalkInChannel(int talkInChannel) {
            this.talkInChannel = talkInChannel;
        }

        @JsonGetter("TalkOutChannel")
        public int getTalkOutChannel() {
            return talkOutChannel;
        }

        @JsonSetter("TalkOutChannel")
        public void setTalkOutChannel(int talkOutChannel) {
            this.talkOutChannel = talkOutChannel;
        }

        @JsonGetter("UpdataTime")
        public String getUpdateTime() {
            return updateTime;
        }

        @JsonSetter("UpdataTime")
        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        @JsonGetter("UpdataType")
        public String getUpdateType() {
            return updateType;
        }

        @JsonSetter("UpdataType")
        public void setUpdateType(String updateType) {
            this.updateType = updateType;
        }

        @JsonGetter("VideoInChannel")
        public int getVideoInChannel() {
            return videoInChannel;
        }

        @JsonSetter("VideoInChannel")
        public void setVideoInChannel(int videoInChannel) {
            this.videoInChannel = videoInChannel;
        }

        @JsonGetter("VideoOutChannel")
        public int getVideoOutChannel() {
            return videoOutChannel;
        }

        @JsonSetter("VideoOutChannel")
        public void setVideoOutChannel(int videoOutChannel) {
            this.videoOutChannel = videoOutChannel;
        }
    }

    private String name;
    private int returnCode;
    private String sessionId;
    private SystemInfoData systemInfo;

    public SystemInfoResponse() {
        super((short)1021);
        systemInfo = new SystemInfoData();
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

    @JsonGetter("SystemInfo")
    public SystemInfoData getSystemInfo() {
        return systemInfo;
    }

    @Override
    public String toString() {
        return "Return Code:" + this.returnCode + " " + this.systemInfo.getSerialNumber() + " " + this.systemInfo.getSoftwareVersion();
    }
}
