package hubertmap.model.graph;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class GraphTest {

    @Test
    void shortestPathTest() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Vertex v3 = new Vertex("C");
        Edge[] edges = {new Edge(10, v1, v2), new Edge(10, v2, v3), new Edge(50, v1, v3)};
        Graph g = new Graph(edges);
        List<Vertex> path = g.shortestPath(v1, v3);
        String pathString = "";
        for (Vertex vertex : path) {
            pathString += vertex.getLabel();
        }
        assertEquals("ABC", pathString);
    }

    void noShortestPathTest() {
        Vertex[] vertices = {new Vertex("A"), new Vertex("B")};
        Graph g = new Graph(vertices);
        List<Vertex> path = g.shortestPath(vertices[0], vertices[1]);
        assertTrue(path.isEmpty());
    }

    void trivialShortestPathTest() {
        Vertex[] vertices = {new Vertex("A")};
        Graph g = new Graph(vertices);
        List<Vertex> path = g.shortestPath(vertices[0], vertices[0]);
        assertEquals(1, path.size());
    }

    void oneWayPathTest() {
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        Edge[] edges = {new Edge(10, v1, v2)};
        Graph g = new Graph(edges);
        List<Vertex> path1 = g.shortestPath(v1, v2);
        List<Vertex> path2 = g.shortestPath(v2, v1);
        assertFalse(path1.isEmpty());
        assertTrue(path2.isEmpty());
    }
}
