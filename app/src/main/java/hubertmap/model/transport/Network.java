package hubertmap.model.transport;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents a network of stations and edges between them, forming a transport network.
 */
public class Network {
    Graph<Station, EdgeTransport> graph;
    HashMap<String, Station> stations;
    double minimumLongitude;
    double maximumLongitude;
    double minimumLatitude;
    double maximumLatitude;

    DijkstraShortestPath distancePaths;

    public Network(Collection<EdgeTransport> edges) {
        graph = new SparseGraph<>();
        stations = new HashMap<>();
        minimumLongitude = 180;
        maximumLongitude = -180;
        minimumLatitude = 90;
        maximumLatitude = -90;

        if (edges != null) {
            for (EdgeTransport e : edges) {
                addEdge(e);
            }
        }

        distancePaths =
                new DijkstraShortestPath(
                        graph,
                        (Object e) -> {
                            return ((EdgeTransport) e).getDistance();
                        });
    }

    public Network() {
        this(null);
    }

    public void addEdge(EdgeTransport edge, Station station1, Station station2) {
        graph.addEdge(edge, station1, station2);

        if (station1.getX() < minimumLongitude) minimumLongitude = station1.getX();
        if (station1.getX() > maximumLongitude) maximumLongitude = station1.getX();

        if (station1.getY() < minimumLatitude) minimumLatitude = station1.getY();
        if (station1.getY() > maximumLatitude) maximumLatitude = station1.getY();

        if (station2.getX() < minimumLongitude) minimumLongitude = station2.getX();
        if (station2.getX() > maximumLongitude) maximumLongitude = station2.getX();

        if (station2.getY() < minimumLatitude) minimumLatitude = station2.getY();
        if (station2.getY() > maximumLatitude) maximumLatitude = station2.getY();

        stations.putIfAbsent(station1.getName(), station1);
        stations.putIfAbsent(station2.getName(), station2);
    }

    public void addEdge(EdgeTransport edge) {
        addEdge(edge, edge.getStartingStation(), edge.getEndingStation());
    }

    public Graph<Station, EdgeTransport> getGraph() {
        return graph;
    }

    public double getMinimumLongitude() {
        return minimumLongitude;
    }

    public double getMaximumLatitude() {
        return maximumLatitude;
    }

    public double getMinimumLatitude() {
        return minimumLatitude;
    }

    public double getMaximumLongitude() {
        return maximumLongitude;
    }

    /**
     * Calculates the shortest path from station1 to station2 using the distance between two
     * stations as weight
     *
     * @param station1 starting station
     * @param station2 destination station
     * @return a list edges to visit in the correct order
     */
    public List<EdgeTransport> shortestPath(Station station1, Station station2) {
        return distancePaths.getPath(station1, station2);
    }

    /**
     * Calculates the shortest path from station1 to station2 using the distance between two
     * stations as weight
     *
     * @param station1 name of the starting station
     * @param station2 name of the destination station
     * @return a list edges to visit in the correct order
     */
    public List<EdgeTransport> shortestPath(String station1, String station2) {
        return shortestPath(stations.get(station1), stations.get(station2));
    }
}
