package com.jbr.exp.tfl.manage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbr.exp.tfl.manage.api.StopPoint;
import com.jbr.exp.tfl.manage.api.StopsReponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class CallTflApi {
    private static Logger log = LoggerFactory.getLogger(CallTflApi.class);

    @Autowired
    StopCalculator stopCalculator;

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
    }
}
