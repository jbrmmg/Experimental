package com.jbr.exp.messages.incoming;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.apache.commons.lang3.ArrayUtils;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkResponse extends BaseResponse {
    public static class NetworkInfo {
        private String gateway;
        private String hostIP;
        private String hostname;
        private int httpPort;
        private String macAddress;
        private int maxBPS;
        private String monMode;
        private int sslPort;
        private String subMask;
        private int tcpMaxConn;
        private int tcpPort;
        private String transferPlan;
        private int udpPort;
        private boolean useHsDownload;

        private InetAddress getInetAddressFromHexString(String ipAsHex) {
            try {
                int inetValue = Integer.decode(getHostIP());

                byte[] bytes = BigInteger.valueOf(inetValue).toByteArray();
                ArrayUtils.reverse(bytes);
                return InetAddress.getByAddress(bytes);
            } catch (Exception e) {
            }

            return null;
        }

        @JsonGetter("GateWay")
        public String getGateway() {
            return gateway;
        }

        @JsonSetter("GateWay")
        public void setGateway(String gateway) {
            this.gateway = gateway;
        }

        @JsonGetter("HostIP")
        public String getHostIP() {
            return hostIP;
        }

        @JsonIgnore
        public InetAddress getHostInetAddress() {
            return getInetAddressFromHexString(getHostIP());
        }

        @JsonSetter("HostIP")
        public void setHostIP(String hostIP) {
            this.hostIP = hostIP;
        }

        @JsonGetter("HostName")
        public String getHostname() {
            return hostname;
        }

        @JsonSetter("HostName")
        public void setHostname(String hostname) {
            this.hostname = hostname;
        }

        @JsonGetter("HttpPort")
        public int getHttpPort() {
            return httpPort;
        }

        @JsonSetter("HttpPort")
        public void setHttpPort(int httpPort) {
            this.httpPort = httpPort;
        }

        @JsonGetter("MAC")
        public String getMacAddress() {
            return macAddress;
        }

        @JsonSetter("MAC")
        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        @JsonGetter("MaxBps")
        public int getMaxBPS() {
            return maxBPS;
        }

        @JsonSetter("MaxBps")
        public void setMaxBPS(int maxBPS) {
            this.maxBPS = maxBPS;
        }

        @JsonGetter("MonMode")
        public String getMonMode() {
            return monMode;
        }

        @JsonSetter("MonMode")
        public void setMonMode(String monMode) {
            this.monMode = monMode;
        }

        @JsonGetter("SSLPort")
        public int getSslPort() {
            return sslPort;
        }

        @JsonSetter("SSLPort")
        public void setSslPort(int sslPort) {
            this.sslPort = sslPort;
        }

        @JsonGetter("Submask")
        public String getSubMask() {
            return subMask;
        }

        @JsonSetter("Submask")
        public void setSubMask(String subMask) {
            this.subMask = subMask;
        }

        @JsonGetter("TCPMaxConn")
        public int getTcpMaxConn() {
            return tcpMaxConn;
        }

        @JsonSetter("TCPMaxConn")
        public void setTcpMaxConn(int tcpMaxConn) {
            this.tcpMaxConn = tcpMaxConn;
        }

        @JsonGetter("TCPPort")
        public int getTcpPort() {
            return tcpPort;
        }

        @JsonSetter("TCPPort")
        public void setTcpPort(int tcpPort) {
            this.tcpPort = tcpPort;
        }

        @JsonGetter("TransferPlan")
        public String getTransferPlan() {
            return transferPlan;
        }

        @JsonSetter("TransferPlan")
        public void setTransferPlan(String transferPlan) {
            this.transferPlan = transferPlan;
        }

        @JsonGetter("UDPPort")
        public int getUdpPort() {
            return udpPort;
        }

        @JsonSetter("UDPPort")
        public void setUdpPort(int udpPort) {
            this.udpPort = udpPort;
        }

        @JsonGetter("UseHSDownLoad")
        public boolean isUseHsDownload() {
            return useHsDownload;
        }

        @JsonSetter("UseHSDownLoad")
        public void setUseHsDownload(boolean useHsDownload) {
            this.useHsDownload = useHsDownload;
        }
    }

    private final NetworkInfo networkInfo;

    public NetworkResponse() {
        super((short)1043);
        networkInfo = new NetworkInfo();
    }

    @JsonGetter("NetWork.NetCommon")
    public NetworkInfo getNetworkInfo() {
        return networkInfo;
    }

    @Override
    public String toString() {
        return super.toString() + " " + this.getNetworkInfo().getHostInetAddress();
    }
}
