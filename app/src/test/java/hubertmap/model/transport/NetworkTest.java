package hubertmap.model.transport;

import static org.junit.jupiter.api.Assertions.*;

import hubertmap.model.DurationJourney;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class NetworkTest {

    @Test
    void shortestPathTest() {
        Station a = new Station("A", "", 0.0f, 0.0f);
        Station b = new Station("B", "", 0.0f, 0.0f);
        Station c = new Station("C", "", 0.0f, 0.0f);

        EdgeTransport ab = new EdgeTransport(new DurationJourney("00", "00"), 10.0f, a, b);
        EdgeTransport bc = new EdgeTransport(new DurationJourney("00", "00"), 10.0f, b, c);
        EdgeTransport ac = new EdgeTransport(new DurationJourney("00", "00"), 50.0f, a, c);

        ArrayList<EdgeTransport> edges = new ArrayList<>();
        edges.add(ab);
        edges.add(bc);
        edges.add(ac);

        Network g = new Network(edges);
        List<EdgeTransport> path = g.shortestPath(a, c);

        List<EdgeTransport> expectedPath = new ArrayList<EdgeTransport>();
        expectedPath.add(ab);
        expectedPath.add(bc);

        assertEquals(expectedPath, path);
    }

    @Test
    void noShortestPathTest() {
        Station a = new Station("A", "", 0.0f, 0.0f);
        Station b = new Station("B", "", 0.0f, 0.0f);
        Station c = new Station("C", "", 0.0f, 0.0f);
        Station d = new Station("D", "", 0.0f, 0.0f);
        ArrayList<EdgeTransport> edges = new ArrayList<>();
        edges.add(new EdgeTransport(new DurationJourney("00", "00"), 1.0f, a, b));
        edges.add(new EdgeTransport(new DurationJourney("00", "00"), 1.0f, c, d));
        Network g = new Network(edges);
        List<EdgeTransport> path = g.shortestPath(a, c);
        assertTrue(path.isEmpty());
    }

    @Test
    void trivialShortestPathTest() {
        Station a = new Station("A", "", 0.0f, 0.0f);
        Station b = new Station("B", "", 0.0f, 0.0f);
        ArrayList<EdgeTransport> edges = new ArrayList<>();
        edges.add(new EdgeTransport(new DurationJourney("00", "00"), 1.0f, a, b));
        Network g = new Network(edges);
        List<EdgeTransport> path = g.shortestPath(a, a);
        assertTrue(path.isEmpty());
    }
}
