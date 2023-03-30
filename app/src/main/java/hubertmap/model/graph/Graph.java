package hubertmap.model.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    private HashMap<Vertex, List<Edge>> vertexEdges;

    public Graph(List<Edge> edges) {
        vertexEdges = new HashMap<>();
        for (Edge edge : edges) {
            vertexEdges.putIfAbsent(edge.getV1(), new ArrayList<>());
            vertexEdges.get(edge.getV1()).add(edge);
        }
    }

    public List<Vertex> shortestPath(Vertex start, Vertex end) {
        for (Vertex ve : vertexEdges.keySet()) {
            ve.setDistance(Float.POSITIVE_INFINITY);
            ve.setVisited(false);
        }
        end.setDistance(0);
        boolean hasPath = calculateDistance(end, start, new ArrayList<>());
        ArrayList<Vertex> vertices = new ArrayList<>();
        if (hasPath) {
            tracePath(end, start, vertices);
        }
        return vertices; // throw exception when no result ?

    }

    // does dijkstra until dest is found
    private boolean calculateDistance(Vertex current, Vertex dest, List<Vertex> toVisit) {
        if (current == dest) {
            return true;
        }
        current.setVisited(true);
        var edges = vertexEdges.get(current);
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

    private void tracePath(Vertex cur, Vertex end, List<Vertex> path) {
        path.add(cur);
        if (cur != end) {
            List<Edge> edges = vertexEdges.get(cur);
            Vertex min = edges.get(0).getV2();
            for (Edge e : edges) {
                if (e.getV2().getDistance() < min.getDistance()) {
                    min = e.getV2();
                }
            }
            if (cur.getDistance() <= min.getDistance()) {
                // this check avoids infinite loops
                throw new RuntimeException("error in tracePath");
            }
            tracePath(min,  end,  path);
        }
    }
}
