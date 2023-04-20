package hubertmap.controller;

import hubertmap.model.parser.Parser;
import hubertmap.model.transport.Network;
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
        network = parser.getNetwork();
        graphView = new GraphData(network.getGraph());
        view = new View(graphView);
    }

    /**
     * Calculates the shortest path between two stations and updates the view accordingly.
     *
     * @param station1Name the name of the starting station
     * @param station2Name the name of the destination station
     */
    public static void setShortestPath(String station1Name, String station2Name) {
        view.setShortestPath(network.shortestPath(station1Name, station2Name));
    }
}
