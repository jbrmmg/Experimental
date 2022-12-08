package com.jbr.exp.tfl.manage.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteStopPoint {
    public int routeId;
    public String stationId;
    public String name;
}
