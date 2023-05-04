package hubertmap.model;

import edu.uci.ics.jung.graph.Graph;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.VertexTransport;
import java.util.*;

public class Dijkstra {

    public static List<EdgeTransport> shortestPath(
            Graph<VertexTransport, EdgeTransport> graph,
            VertexTransport source,
            VertexTransport destination) {

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
                        weight += 300; // add 5 minutes to distance if weight don't match
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

        return path;
    }
}
