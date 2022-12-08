package com.jbr.exp.tfl.manage.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StopPointSequence {
    public String lineId;
    public RouteStopPoint[] stopPoint;
}
