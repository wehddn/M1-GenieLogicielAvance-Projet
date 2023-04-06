package hubertmap.controller;

import edu.uci.ics.jung.graph.Graph;
import hubertmap.model.parser.Parser;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Station;
import hubertmap.view.View;

/**
 * The Controller class is responsible for managing the interaction between the Model and the View
 * in a Model-View-Controller (MVC) architecture. It handles updates to the model and the view, and
 * ensures that they stay in sync with each other.
 */
public class Controller {

    /** Constructs a new Controller instance with the given View and Graph objects. */
    public Controller() {
        Parser parser = new Parser();
        Graph<Station, EdgeTransport> graph = parser.getEdges();
        new View(graph);
    }
}
