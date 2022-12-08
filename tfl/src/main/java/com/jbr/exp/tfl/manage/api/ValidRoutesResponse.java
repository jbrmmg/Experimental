package com.jbr.exp.tfl.manage.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidRoutesResponse {
    public String lineId;
    public String lineName;
    public StopPointSequence[] stopPointSequences;
}
