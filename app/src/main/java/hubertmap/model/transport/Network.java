package hubertmap.model.transport;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import hubertmap.model.DurationJourney;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a network of vertex and edges between them, forming a transport network.
 */
public class Network {
    Graph<VertexTransport, EdgeTransport> graph;
    HashMap<String, VertexTransport> stations;
    List<EdgeTransport> shortestPath;

    DijkstraShortestPath<VertexTransport, EdgeTransport> distancePaths;
    private Map<Line, ArrayList<DurationJourney>> datatLine;

    int pointCount = 0;

    /**
     * Constructs a new Network object with the given collection of edges.
     *
     * @param edges the collection of edges to add to the network
     */
    public Network(Collection<EdgeTransport> edges) {
        graph = new SparseGraph<>();
        stations = new HashMap<>();
        shortestPath = new ArrayList<>();

        if (edges != null) {
            for (EdgeTransport e : edges) {
                addEdge(e);
            }
        }

        distancePaths = new DijkstraShortestPath<>(graph, EdgeTransport::estimateWeight);
    }
    /** Constructs a new Network object with no edges. */
    public Network() {
        this(null);
    }
    /**
     * Adds an edge to the network, connecting the given stations.
     *
     * @param edge the edge to add
     * @param vertexTransport the first station to connect
     * @param vertexTransport2 the second station to connect
     */
    public void addEdge(
            EdgeTransport edge, VertexTransport vertexTransport, VertexTransport vertexTransport2) {
        graph.addEdge(edge, vertexTransport, vertexTransport2);

        stations.putIfAbsent(vertexTransport.getName().toLowerCase(), vertexTransport);
        stations.putIfAbsent(vertexTransport2.getName().toLowerCase(), vertexTransport2);
    }

    /**
     * Adds an edge to the network, connecting the stations at the start and end of the edge.
     *
     * @param edge the edge to add
     */
    public void addEdge(EdgeTransport edge) {
        addEdge(edge, edge.getStartingStation(), edge.getEndingStation());
    }

    /**
     * Returns the graph representing the network.
     *
     * @return the graph representing the network
     */
    public Graph<VertexTransport, EdgeTransport> getGraph() {
        return graph;
    }

    /**
     * Calculates the shortest path from station1 to station2 using the distance between two
     * stations as weight
     *
     * <p>for each edge returned, swaps starting and ending stations if needed, corresponding with
     * the direction taken
     *
     * @param station1 starting station
     * @param station2 destination station
     * @return a list edges to visit in the correct order
     */
    public List<EdgeTransport> shortestPath(VertexTransport station1, VertexTransport station2) {
        List<EdgeTransport> list = distancePaths.getPath(station1, station2);
        VertexTransport s = station1;
        for (EdgeTransport e : list) {
            if (!e.getStartingStation().equals(s)) {
                e.swapStations();
            }
            s = e.getEndingStation();
        }
        return list;
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
        if (stations.get(station1) != null && stations.get(station2) != null)
            return shortestPath(stations.get(station1), stations.get(station2));
        else return null;
    }

    /**
     * Set the list of lines in the network with their duration.
     *
     * @param dataLine the list of lines with their duration
     */
    public void setDataLine(Map<Line, ArrayList<DurationJourney>> dataLine) {
        this.datatLine = dataLine;
    }

    /**
     * Returns the list of lines in the network.
     *
     * @return the list of lines in the network
     */
    public Set<Line> getLines() {
        return datatLine.keySet();
    }

    /**
     * Uses the input path to create an abstracted vestion of it, were adjascent path on the same
     * line are merged into a single path
     *
     * @param path list of EdgeTransport
     * @return a new list of EdgeTransport
     */
    public List<EdgeTransport> simplifiedPath(List<EdgeTransport> path) {
        List<EdgeTransport> simplePath = new ArrayList<>();
        if (path.isEmpty()) {
            return simplePath;
        }

        EdgeTransport prevEdge = path.get(0);
        VertexTransport prevStation = prevEdge.getStartingStation();

        float distance = prevEdge.getDistance();
        DurationJourney duration = prevEdge.getDurationJourney().copy();

        for (int i = 1; i < path.size() - 1; i++) {
            EdgeTransport e = path.get(i);
            if (!e.getLineName().equals(prevEdge.getLineName())) {
                simplePath.add(
                        new EdgeTransport(
                                prevStation,
                                e.getStartingStation(),
                                duration,
                                distance,
                                prevEdge.getLineName()));
                prevStation = e.getStartingStation();
                prevEdge = e;
                distance = e.getDistance();
                duration = e.getDurationJourney().copy();
            } else {
                distance += e.getDistance();
                duration.add(e.getDurationJourney());
            }
        }

        EdgeTransport lastEdge = path.get(path.size() - 1);

        if (lastEdge.getLineName().equals(prevEdge.getLineName())) {
            distance += lastEdge.getDistance();
            duration.add(lastEdge.getDurationJourney());
            simplePath.add(
                    new EdgeTransport(
                            prevStation,
                            lastEdge.getEndingStation(),
                            duration,
                            distance,
                            lastEdge.getLineName()));
        } else {
            simplePath.add(
                    new EdgeTransport(
                            prevStation,
                            lastEdge.getStartingStation(),
                            duration,
                            distance,
                            prevEdge.getLineName()));
            simplePath.add(lastEdge.copy());
        }

        return simplePath;
    }

    public String createPoint(double x, double y) {
        Point p = new Point("point" + pointCount, x, y);
        pointCount++;
        ArrayList<EdgeTransport> newEdges = new ArrayList<>();
        for (VertexTransport v : graph.getVertices()) {
            float distance = calculateDistance(p.getX(), p.getY(), v.getX(), v.getY());
            DurationJourney durationJourney = calculateDurationJourney(distance);
            newEdges.add(new EdgeTransport((VertexTransport) p, v, durationJourney, distance, ""));
        }

        for (EdgeTransport edgeTransport : newEdges) {
            addEdge(edgeTransport);
        }

        return p.getName();
    }

    public static float calculateDistance(float x1, float y1, float x2, float y2) {
        float earthRadius = 6371.0f; // Earth's radius in kilometers
        float dLat = (float) Math.toRadians(x2 - x1);
        float dLon = (float) Math.toRadians(y2 - y1);
        float lat1 = (float) Math.toRadians(x1);
        float lat2 = (float) Math.toRadians(x2);

        float a =
                (float)
                        (Math.sin(dLat / 2) * Math.sin(dLat / 2)
                                + Math.sin(dLon / 2)
                                        * Math.sin(dLon / 2)
                                        * Math.cos(lat1)
                                        * Math.cos(lat2));
        float c = (float) (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
        float distance = earthRadius * c;

        return distance * 10; // distance is in 10th of km
    }

    public static DurationJourney calculateDurationJourney(float distance) {
        distance /= 10; // putting distance back in km
        int walkingSpeed = 5; // km/h
        int walkingTimeInSeconds = (int) Math.round((distance / walkingSpeed) * 3600);
        return new DurationJourney(walkingTimeInSeconds);
    }
}
