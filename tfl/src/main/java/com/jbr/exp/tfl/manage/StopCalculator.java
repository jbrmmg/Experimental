package com.jbr.exp.tfl.manage;

import com.jbr.exp.tfl.data.ConnectionRepository;
import com.jbr.exp.tfl.data.LineRepository;
import com.jbr.exp.tfl.data.StationRepository;
import com.jbr.exp.tfl.data.entity.Connection;
import com.jbr.exp.tfl.data.entity.Line;
import com.jbr.exp.tfl.data.entity.Station;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StopCalculator {
    private static Logger log = LoggerFactory.getLogger(StopCalculator.class);

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    Map<String, Line> lines = new HashMap<>();
    Map<String, Station> stations = new HashMap<>();
    Map<String, Connection> connections = new HashMap<>();

    public void initialise() {
        log.info("Initialise");

        // Load lines.
        for(Line line : lineRepository.findAll()) {
            lines.put(line.getId(),line);
        }
        log.info(lines.keySet().size() + " lines loaded.");

        // Load stations.
        for(Station station : stationRepository.findAll()) {
            stations.put(station.getId(),station);
        }
        log.info(stations.keySet().size() + " stations loaded.");

        // Connections
        for(Connection connection : connectionRepository.findAll()) {
            connections.put(connection.getId(),connection);
        }
        log.info(connections.keySet().size() + " connections loaded.");
    }

    public void process(Map<String,String> data) {
        for(Map.Entry<String,String> next : data.entrySet()) {
            if(stations.containsKey(next.getKey())) {
                stations.get(next.getKey()).setFullId(next.getValue());
            }
        }

        for(Station station : stations.values()) {
            log.info(station.getId() + "," +
                    station.getName() + "," +
                    station.getFullId() + "," +
                    station.getZone1() + "," +
                    ( station.hasZone2() ? station.getZone2() : "") );
        }
    }
}
