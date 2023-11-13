package com.jbr.exp.uiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.codec.ServerSentEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.codec.ServerSentEvent.builder;

public class MonitorInstance {
    private static final Logger LOG = LoggerFactory.getLogger(MonitorInstance.class);
    private static int id = 1;
    private int instanceId;

    public MonitorInstance() {
        LOG.info("Created instance {}",this);
        this.instanceId = id++;
    }

    public ServerSentEvent<String> test(long a) {
        LOG.info("test {} {}", this.instanceId,this);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

        return ServerSentEvent.<String> builder()
                .data("blah! " + instanceId + " " +  LocalDateTime.now().format(formatter))
                .build();
    }
}
