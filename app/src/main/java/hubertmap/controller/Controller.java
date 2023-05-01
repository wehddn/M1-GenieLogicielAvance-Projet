package hubertmap.controller;

import hubertmap.model.DurationJourney;
import hubertmap.model.parser.Parser;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Network;
import hubertmap.model.transport.Point;
import hubertmap.model.transport.Station;
import hubertmap.model.transport.VertexTransport;
import hubertmap.view.GraphData;
import hubertmap.view.View;

/**
 * The Controller class is responsible for managing the interaction between the Model and the View
 * in a Model-View-Controller (MVC) architecture. It handles updates to the model and the view, and
 * ensures that they stay in sync with each other.
 */
public class Controller {

    /* The network of stations and edges managed in Model */
    static Network network;

    /* The GUI of application. */
    static View view;

    GraphData graphView;

    /** Constructs a new Controller instance, creates Network and View */
    public Controller() {
        Parser parser = new Parser();
        network = parser.getEdges();

        Point p = new Point("test", 2.3408628540691043f, 48.84205232329236f);

        VertexTransport s = null;
        for (VertexTransport v : network.getGraph().getVertices()) {
            if (v.getName().equals("Vavin")) s = v;
        }

        EdgeTransport e =
                new EdgeTransport((VertexTransport) p, s, new DurationJourney("1", "1"), 0, "");

        network.addEdge(e, (VertexTransport) p, s);

        graphView = new GraphData(network.getGraph(), network.getLines());
        view = new View(graphView);
        Controller.setShortestPath("test", "pernety");
    }

    /**
     * Calculates the shortest path between two stations and updates the view accordingly.
     *
     * @param station1Name the name of the starting station
     * @param station2Name the name of the destination station
     */
    public static void setShortestPath(String station1Name, String station2Name) {
        var shortestPath = network.shortestPath(station1Name, station2Name);
        // var simplifiedPath = network.simplifiedPath(shortestPath);
        // TODO: use simplifiedPath for something
        /*
         * uncommenting these lines helps to understand the function. to delete later
         * for (EdgeTransport e : simplifiedPath) {
         * System.out.println(e);
         * }
         */
        view.setShortestPath(shortestPath);
    }

    public static void setDeparture(String name) {
        view.setDeparture(name);
    }

    public static void setArrival(String name) {
        view.setArrival(name);
    }

    public static void setSchedules(Station v) {
        view.setSchedules(v);
    }
}
