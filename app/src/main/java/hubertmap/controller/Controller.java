package hubertmap.controller;

import hubertmap.model.graph.Graph;
import hubertmap.model.parser.Parser;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.view.Panel;
import hubertmap.view.View;
import java.util.ArrayList;

/**
 * The Controller class is responsible for managing the interaction between the Model and the View
 * in a Model-View-Controller (MVC) architecture. It handles updates to the model and the view, and
 * ensures that they stay in sync with each other.
 */
public class Controller {

    /** The panel that displays the graph and other elements in the View. */
    private static Panel panel;

    /** The graph model that the Controller is managing. */
    private Graph graph;

    /** Constructs a new Controller instance with the given View and Graph objects. */
    public Controller() {
        Parser parser = new Parser();
        ArrayList<EdgeTransport> edges = parser.getEdges();
        View view = new View(edges);
        panel = view.getPanel();
        graph = new Graph();
    }
}
