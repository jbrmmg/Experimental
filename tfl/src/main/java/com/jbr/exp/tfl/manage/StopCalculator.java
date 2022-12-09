package com.jbr.exp.tfl.manage;

import com.jbr.exp.tfl.data.ConnectionRepository;
import com.jbr.exp.tfl.data.LineRepository;
import com.jbr.exp.tfl.data.RouteRepository;
import com.jbr.exp.tfl.data.StationRepository;
import com.jbr.exp.tfl.data.entity.Connection;
import com.jbr.exp.tfl.data.entity.Line;
import com.jbr.exp.tfl.data.entity.Route;
import com.jbr.exp.tfl.data.entity.Station;
import graph.Graph;
import graph.Node;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class StopCalculator {
    private static Logger log = LoggerFactory.getLogger(StopCalculator.class);

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private RouteRepository routeRepository;

    Map<String, Line> lines = new HashMap<>();
    Map<String, Station> stations = new HashMap<>();
    Map<String, Connection> connections = new HashMap<>();

    private Graph calculateShortest(Station station) {
        Map<String, Node> nodeMap = new HashMap<>();
        Node source = null;
        for(Map.Entry<String,Station> nextStation : stations.entrySet()) {
            nodeMap.put(nextStation.getKey(),new Node(nextStation.getKey()));

            if(nextStation.getValue().getId().equals(station.getId())) {
                source = nodeMap.get(nextStation.getKey());
            }
        }

        for(Map.Entry<String,Connection> nextConnection : connections.entrySet()) {
            String[] keys = nextConnection.getKey().split("-");

            Node node1 = nodeMap.get(keys[0]);
            Node node2 = nodeMap.get(keys[1]);

            node1.addDestination(node2,1);
            node2.addDestination(node1,1);
        }

        Graph result = new Graph();

        for(Map.Entry<String,Node> nextNode : nodeMap.entrySet()) {
            result.addNode(nextNode.getValue());
        }

        return Graph.calculateShortestPathFromSource(result,source);
    }

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

        // Calculate the shortest route for station.
        for(Map.Entry<String,Station> next : stations.entrySet()) {
//            if(next.getKey().equals("PNR")) {
                Graph shortest = calculateShortest(next.getValue());

                for(Node nextNode : shortest.getNodes()) {
                    String id = Station.getConnectionId(nextNode.getName(),next.getKey());

                    Optional<Route> alreadyExist = routeRepository.findById(id);
                    if(alreadyExist.isEmpty()) {
                        int stops = nextNode.getDistance();
                        Station other = stations.get(nextNode.getName());
                        int zones = Station.zoneDifference(next.getValue(),other);

                        Route newRoute = new Route();
                        newRoute.setId(id);
                        newRoute.setStops(stops);
                        newRoute.setZones(zones);
                        newRoute.setStation1(next.getValue().getName());
                        newRoute.setStation2(other.getName());

                        routeRepository.save(newRoute);
                    }
//                }
                }
            log.info("here");
        }
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

    public boolean validateStation(String stationId) {
        for(Station station : stations.values()) {
            if(station.getFullId().equals(stationId)) {
                return true;
            }
        }

        return false;
    }

    public Station getStationWithFullId(String fullId) {
        for(Station station : stations.values()) {
            if(station.getFullId().equals(fullId)) {
                return station;
            }
        }

        return null;
    }
}
