package com.jbr.exp.tfl.manage;

import ch.qos.logback.core.recovery.ResilientOutputStreamBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbr.exp.tfl.data.entity.Station;
import com.jbr.exp.tfl.manage.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CallTflApi {
    private static Logger log = LoggerFactory.getLogger(CallTflApi.class);

    @Autowired
    StopCalculator stopCalculator;

    private ValidRoutesResponse getRouteStops(String line) {
        try {
            String url = "https://api.tfl.gov.uk/Line/" + line + "/Route/Sequence/inbound?serviceTypes=Regular";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(result, ValidRoutesResponse.class);
        } catch (Exception e) {
            log.error("Failure",e);
        }

        return null;
    }

    public void callApi2() {
        try {
            //https://api.tfl.gov.uk/Journey/JourneyResults/{from}/to/{to}

            Map<String,String> connections = new HashMap<>();
            for(String nextLine : "metropolitan:MET,bakerloo:BKL,central:CTR,circle:CIR,district:DST,hammersmith-city:HAC,jubilee:JUB,northern:NOR,piccadilly:PIC,victoria:VIC,waterloo-city:WAC".split(",")) {
                String[] nextLineElements = nextLine.split(":");

                ValidRoutesResponse validRoutesResponse = getRouteStops(nextLineElements[0]);

                // Get the connections.
                for(StopPointSequence next : validRoutesResponse.stopPointSequences) {
                    Station previous = null;
                    for(RouteStopPoint next2 : next.stopPoint) {
                        Station nextStation = stopCalculator.getStationWithFullId(next2.stationId);

                        if(previous != null) {
                            String connectionId = Station.getConnectionId(nextStation,previous);

                            if(!connections.containsKey(connectionId)) {
                                connections.put(connectionId,nextLineElements[1] + "," + nextStation.getId() + "," + previous.getId());
                            }
                        }

                        previous = nextStation;
                    }
                }
            }

            for(Map.Entry<String,String> next : connections.entrySet()) {
                log.info(next.getKey() + "," + next.getValue());
            }

            /*
            for(Map.Entry<String,String> nextId : invalidIds.entrySet()) {
                String id = nextId.getKey().substring(nextId.getKey().length() - 3);
                String fullId = nextId.getKey();
                String name = nextId.getValue().replace(" Underground Station","");
                log.info(id + "," + name + "," + fullId + ",,");
            }
             */

            log.info("here");
        } catch(Exception e) {
            log.error("Failure",e);
        }
    }

    public void callApi() {
        try {
            //https://api.tfl.gov.uk/StopPoint/Mode/tube[?page]
            log.info("CallApi");

            String url = "https://api.tfl.gov.uk/StopPoint/Mode/tube";
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();

            StopsReponse stopsReponse = objectMapper.readValue(result, StopsReponse.class);

            // Find all the distinct stations.
            Map<String,String> stationFullId = new HashMap<>();
            for(StopPoint stopPoint : stopsReponse.stopPoints) {
                String fullId = stopPoint.stationNaptan;
                if(fullId != null && fullId.length() >= 3) {
                    String id = fullId.substring(fullId.length() - 3);
                    if (!stationFullId.containsKey(id)) {
                        stationFullId.put(id, fullId);
                    }
                }
            }

            stopCalculator.process(stationFullId);
            log.info("here");
        } catch(Exception e) {
            log.error("Failure",e);
        }
    }

    public void process() {
//        callApi();
//        callApi2();
    }
}
