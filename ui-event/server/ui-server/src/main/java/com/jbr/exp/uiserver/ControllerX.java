package com.jbr.exp.uiserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/jbr")
public class ControllerX {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerX.class);

    private final Flux<ServerSentEvent<Response>> generator;

    public ControllerX() {
        generator = Flux.interval(Duration.ofSeconds(1))
                .map(this::test);
    }

    private ServerSentEvent<Response> test(long a) {
        LOG.info("test {}",this);

        return ServerSentEvent.<Response> builder()
                .data(new Response(LocalDateTime.now()))
                .build();
    }

    @GetMapping(path="/int/file-updates",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<Response>> fileUpdate(@RequestParam(value = "id", defaultValue = "UNKN") String id) {
        try {
            LOG.info("ID {}",id);
            return generator;
        } catch(Exception e) {
            LOG.error(e.getMessage());
        }

        return null;
    }

    @GetMapping(path="/int/test") public String test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        LOG.info("here " + LocalDateTime.now().format(formatter));
        return "here";
    }
}
