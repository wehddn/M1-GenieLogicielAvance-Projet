package hubertmap.model.transport;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import hubertmap.model.DurationJourney;
import hubertmap.model.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class represents a network of vertex and edges between them, forming a transport network.
 */
public class Network {
    static Graph<VertexTransport, EdgeTransport> graph;
    HashMap<String, VertexTransport> stations;
    List<EdgeTransport> shortestPath;
    List<Point> userPoints;

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
        userPoints = new ArrayList<>();

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
        if (stations.get(station1) != null && stations.get(station2) != null) {
            List<EdgeTransport> path = shortestPath(stations.get(station1), stations.get(station2));
            // omits line changes at the extremities
            if (!path.isEmpty() && path.get(0).getLineName().equals("CHANGE")) {
                path.remove(0);
            }
            if (!path.isEmpty() && path.get(path.size() - 1).getLineName().equals("CHANGE")) {
                path.remove(path.size() - 1);
            }
            return path;
        } else return null;
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

    /**
     * Creates a point defined by the coordinates provided by the user
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return name of created point
     */
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

        userPoints.add(p);

        return p.getName();
    }

    /**
     * Calculates the distance between two points on Earth using the Haversine formula.
     *
     * @param x1 the latitude of the first point in degrees
     * @param y1 the longitude of the first point in degrees
     * @param x2 the latitude of the second point in degrees
     * @param y2 the longitude of the second point in degrees
     * @return the distance between the two points in 10th of km
     */
    public float calculateDistance(float x1, float y1, float x2, float y2) {
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

    /**
     * Calculates the duration of a journey based on the given distance and a walking speed of 5
     * km/h.
     *
     * @param distance the distance of the journey in 10th of km
     * @return the duration of the journey as a {@link DurationJourney} object
     */
    public DurationJourney calculateDurationJourney(float distance) {
        distance /= 10; // putting distance back in km
        int walkingSpeed = 5; // km/h
        int walkingTimeInSeconds = (int) Math.round((distance / walkingSpeed) * 3600);
        return new DurationJourney(walkingTimeInSeconds);
    }

    /** Deletes all user points from the graph. */
    public void deleteUserPoints() {
        for (Point point : userPoints) {
            graph.removeVertex(point);
            stations.values().remove(point);
        }
    }

    public static List<EdgeTransport> shortestPathChanges(
            VertexTransport source, VertexTransport destination) {

        Map<VertexTransport, Integer> weights = new HashMap<>();
        Map<VertexTransport, EdgeTransport> previousEdges = new HashMap<>();

        PriorityQueue<VertexTransport> pq =
                new PriorityQueue<>(
                        (n1, n2) ->
                                weights.getOrDefault(n1, Integer.MAX_VALUE)
                                        - weights.getOrDefault(n2, Integer.MAX_VALUE));

        Set<VertexTransport> visited = new HashSet<>();

        weights.put(source, 0);
        pq.add(source);

        while (!pq.isEmpty()) {
            VertexTransport current = pq.poll();
            visited.add(current);

            for (EdgeTransport edge : graph.getIncidentEdges(current)) {
                VertexTransport neighbor = edge.getOtherStation(current);
                int weight =
                        weights.getOrDefault(current, Integer.MAX_VALUE)
                                + edge.getDurationJourney().toSeconds();

                if (previousEdges.get(current) != null)
                    if (!edge.getLineName().equals(previousEdges.get(current).getLineName())) {
                        weight += 120; // add 2 minutes to distance if weight don't match
                    }

                if (!visited.contains(neighbor)) {
                    pq.add(neighbor);
                }

                if (weight < weights.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    weights.put(neighbor, weight);
                    previousEdges.put(neighbor, edge);
                }
            }
        }

        List<EdgeTransport> path = new ArrayList<>();
        VertexTransport current = destination;
        while (current != null && previousEdges.containsKey(current)) {
            EdgeTransport previousEdge = previousEdges.get(current);
            path.add(previousEdge);
            current = previousEdge.getOtherStation(current);
        }
        Collections.reverse(path);

        VertexTransport currentSort = source;
        for (EdgeTransport edge : path) {
            if (edge.getStartingStation() != currentSort) edge.swapStations();
            currentSort = edge.getEndingStation();
        }

        return path;
    }

    public Pair<Time> getTimes(
            List<EdgeTransport> shortestPath,
            String stationName1,
            String stationName2,
            Time currentTime) {

        // If the names are equal, return the transfer time
        if (stationName1.equals(stationName2)) {
            Time arrivalTime = new Time(currentTime);
            arrivalTime.increaseByMinute(2);
            return new Pair<Time>(currentTime, arrivalTime);
        }
        // Check if both vertices are stations
        else if (stations.get(stationName1.toLowerCase()) instanceof Station
                && stations.get(stationName2.toLowerCase()) instanceof Station) {
            Station station1 = (Station) stations.get(stationName1.toLowerCase());
            Station station2 = (Station) stations.get(stationName2.toLowerCase());

            // Get common variants for both stations
            Set<String> commonLines = new HashSet<>(station1.getSchedules().keySet());
            commonLines.retainAll(station2.getSchedules().keySet());

            // If there are common options, find the time of departure
            if (commonLines.size() > 0) {
                Time departTime = new Time(0, 0, 0);
                for (String lineName : commonLines) {
                    // Check if the departure station is after the arrival station (stations stored
                    // in lines should be sorted)
                    if (endAfterStart(lineName, station1, station2)) {
                        // Find the nearest departure time and the variant to which it belongs
                        Time time = nextDepart(lineName, station1, currentTime);
                        if (time != null && time.compareTo(departTime) >= 0) {
                            departTime = time;
                        }
                    }
                }

                // To find the arrival time, add to the departure time the travel time and 2 minutes
                // for the transfer
                Time arrivalTime = null;
                if (departTime.compareTo(new Time(0, 0, 0)) != 0) {
                    arrivalTime = calculatePathTime(departTime, shortestPath, station1, station2);
                    if (arrivalTime != null) {
                        arrivalTime.increaseByMinute(2);
                        return new Pair<Time>(departTime, arrivalTime);
                    }
                }
            }
        }
        // One of the vertices is a point, return the walking time
        else {
            Time departTime = currentTime;
            VertexTransport v1 = stations.get(stationName1.toLowerCase());
            VertexTransport v2 = stations.get(stationName2.toLowerCase());
            if (v1 != null && v2 != null) {
                Time arrivalTime = calculatePathTime(departTime, shortestPath, v1, v2);
                return new Pair<Time>(departTime, arrivalTime);
            } else return null;
        }

        return null;
    }

    private Time calculatePathTime(
            Time departTime,
            List<EdgeTransport> shortestPath,
            VertexTransport vertexTransport,
            VertexTransport vertexTransport2) {
        Time time = new Time(departTime);
        VertexTransport currentStation = vertexTransport;
        for (EdgeTransport edgeTransport : shortestPath) {
            if (edgeTransport.getStartingStation().equals(currentStation)) {
                currentStation = edgeTransport.getEndingStation();
                time = time.increaseWithADurationJourney(edgeTransport.getDurationJourney());
            }

            if (edgeTransport.getEndingStation().equals(currentStation)) {
                return time;
            }
        }
        return null;
    }

    private Time nextDepart(String lineName, Station station1, Time currentTime) {
        Set<Time> stationTimes = station1.getSchedules().get(lineName);
        if (stationTimes != null) {
            for (Time time : stationTimes) {
                String timeString = time.toString();
                LocalTime stationTime = LocalTime.parse(timeString);
                LocalTime timeToCheck = LocalTime.parse(currentTime.toString());
                if (stationTime.isAfter(timeToCheck)) {
                    return time;
                }
            }
        }
        return null;
    }

    private boolean endAfterStart(String lineName, Station station1, Station station2) {
        Line currentLine = getLineByName(getLines(), lineName);
        if (currentLine != null) {
            ArrayList<Station> lineStations = currentLine.getAllStations();
            int index1 = lineStations.indexOf(station1);
            int index2 = lineStations.indexOf(station2);
            if (index1 < index2) return true;
            else return false;
        }
        return false;
    }

    public Line getLineByName(Set<Line> lines, String name) {
        for (Line line : lines) {
            if (line.getName().equals(name)) {
                return line;
            }
        }
        return null;
    }
}
