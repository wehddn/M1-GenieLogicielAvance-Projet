package hubertmap.model.graph;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GraphTest {

    @Test
    void shortestPathTest() {
        Graph g = new Graph();
        assertEquals(null, g.shortestPath());
    }
}
