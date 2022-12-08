package com.jbr.exp.tfl.manage.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StopsReponse {
    public int[] centrePoint;
    public StopPoint[] stopPoints;
    public int pageSize;
    public int total;
    public int page;
}
