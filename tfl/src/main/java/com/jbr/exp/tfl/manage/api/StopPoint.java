package com.jbr.exp.tfl.manage.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StopPoint {
    public String naptanId;
    public String platformName;
    public String[] modes;
    public String stopType;
    public String stationNaptan;
    public String id;
    public String commonName;
    public String placeType;
    public AdditionalProperty[] additionalProperties;
    public double lat;
    public double lon;
}
