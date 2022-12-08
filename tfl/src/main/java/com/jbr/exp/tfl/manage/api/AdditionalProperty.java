package com.jbr.exp.tfl.manage.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalProperty {
    public String category;
    public String key;
    public String sourceSystemKey;
    public String value;
    public LocalDateTime modified;
}
