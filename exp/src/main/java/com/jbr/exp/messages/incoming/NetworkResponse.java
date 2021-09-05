package com.jbr.exp.messages.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkResponse extends BaseResponse {

    public NetworkResponse() {
        super((short)1043);
    }
}
