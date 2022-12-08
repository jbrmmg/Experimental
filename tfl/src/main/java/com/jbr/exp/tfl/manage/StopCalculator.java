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

    public void initialise() {
        log.info("Initialise");

        // Load lines.
        Map<String, Line> lines = new HashMap<>();
        for(Line line : lineRepository.findAll()) {
            lines.put(line.getId(),line);
        }
        log.info(lines.keySet().size() + " lines loaded.");

        // Load stations.
        Map<String, Station> stations = new HashMap<>();
        for(Station station : stationRepository.findAll()) {
            stations.put(station.getId(),station);
        }
        log.info(stations.keySet().size() + " stations loaded.");

        // Connections
        Map<String, Connection> connections = new HashMap<>();
        for(Connection connection : connectionRepository.findAll()) {
            connections.put(connection.getId(),connection);
        }
        log.info(connections.keySet().size() + " connections loaded.");
    }
}
