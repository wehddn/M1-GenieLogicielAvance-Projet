package hubertmap.model.graph;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void shortestPathTest() {
        Graph g = new Graph();
        assertEquals(null, g.shortestPath());
    }
}