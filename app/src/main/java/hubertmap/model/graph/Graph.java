package hubertmap.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Represents a positively weighted oriented graph */
public class Graph {

    private HashMap<Vertex, List<Edge>> adjacencyList;

    // Inverted graph, used to trace back path
    private HashMap<Vertex, List<Edge>> invAdjacencyList;

    /**
     * Construct a new graph
     *
     * @param vertices array of vertices
     * @param edges array of edges
     */
    public Graph(Vertex[] vertices, Edge[] edges) {
        adjacencyList = new HashMap<>();
        invAdjacencyList = new HashMap<>();
        for (Edge edge : edges) {
            addEdge(edge);
        }
        for (Vertex vertex : vertices) {
            addVertex(vertex);
        }
    }

    /**
     * Construct a new graph
     *
     * @param edges array of edges
     * @param vertices array of vertices
     */
    public Graph(Edge[] edges, Vertex[] vertices) {
        this(vertices, edges);
    }

    /**
     * Construct a new graph vertices are induced from edges
     *
     * @param edges array of edges
     */
    public Graph(Edge[] edges) {
        this(new Vertex[0], edges);
    }

    /**
     * Construct a new graph without any edges
     *
     * @param vertices array of vertices
     */
    public Graph(Vertex[] vertices) {
        this(vertices, new Edge[0]);
    }

    /** Construct a new empty graph */
    public Graph() {
        this(new Vertex[0], new Edge[0]);
    }

    /**
     * Adds a new oriented edge to the graph
     *
     * @param edge
     */
    public void addEdge(Edge edge) {
        adjacencyList.putIfAbsent(edge.getV1(), new ArrayList<>());
        adjacencyList.putIfAbsent(edge.getV2(), new ArrayList<>());
        adjacencyList.get(edge.getV1()).add(edge);

        invAdjacencyList.putIfAbsent(edge.getV1(), new ArrayList<>());
        invAdjacencyList.putIfAbsent(edge.getV2(), new ArrayList<>());
        invAdjacencyList
                .get(edge.getV2())
                .add(new Edge(edge.getWeight(), edge.getV2(), edge.getV1()));
    }

    /**
     * adds a new vertex to the graph
     *
     * @param vertex
     */
    public void addVertex(Vertex vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
        invAdjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * finds the shortest path using dijkstra. returns an empty list if no path is found.
     *
     * @param start starting vertex
     * @param end destination vertex
     * @return a list of vertices corresponding to the shortest path
     */
    public List<Vertex> shortestPath(Vertex start, Vertex end) {
        for (Vertex ve : adjacencyList.keySet()) {
            ve.setDistance(Float.POSITIVE_INFINITY);
            ve.setVisited(false);
        }
        end.setDistance(0);
        boolean hasPath = calculateDistance(end, start, new ArrayList<>());
        ArrayList<Vertex> vertices = new ArrayList<>();
        if (hasPath) {
            tracePath(start, end, vertices);
        }
        return vertices; // throw exception when no result ?
    }

    /**
     * does dijkstra on the inverted graph until dest is found.
     *
     * @param current
     * @param dest
     * @param toVisit
     * @return true if path is found, else false.
     */
    private boolean calculateDistance(Vertex current, Vertex dest, List<Vertex> toVisit) {
        if (current == dest) {
            return true;
        }
        current.setVisited(true);
        var edges = invAdjacencyList.get(current);
        for (Edge e : edges) {
            if (!(e.getV2().isVisited())) {
                float dist1 = e.getV2().getDistance();
                float dist2 = current.getDistance() + e.getWeight();
                float minDist = (dist1 < dist2) ? dist1 : dist2;
                e.getV2().setDistance(minDist);
                toVisit.add(e.getV2());
            }
        }
        if (toVisit.isEmpty()) {
            return false; // no path to dest
        }
        Vertex minVertex = toVisit.get(0);
        for (Vertex v : toVisit) {
            if (v.getDistance() < minVertex.getDistance()) {
                minVertex = v;
            }
        }
        toVisit.remove(minVertex);
        return calculateDistance(minVertex, dest, toVisit);
    }

    /**
     * uses the actual graph to trace back path. puts the result in the variable path
     *
     * @param cur
     * @param end
     * @param path
     */
    private void tracePath(Vertex cur, Vertex end, List<Vertex> path) {
        path.add(cur);
        if (cur != end) {
            List<Edge> edges = adjacencyList.get(cur);
            Vertex min = edges.get(0).getV2();
            for (Edge e : edges) {
                if (e.getV2().getDistance() < min.getDistance()
                        && e.getWeight() == cur.getDistance() - e.getV2().getDistance()) {
                    min = e.getV2();
                }
            }
            if (cur.getDistance() <= min.getDistance()) {
                // this check avoids infinite loops
                throw new RuntimeException("error in tracePath");
            }
            tracePath(min, end, path);
        }
    }
}
