package hubertmap.controller;

import hubertmap.model.parser.Parser;
import hubertmap.model.transport.Network;
import hubertmap.model.transport.Station;
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

    static GraphData graphView;

    /** Constructs a new Controller instance, creates Network and View */
    public Controller() {
        Parser parser = new Parser();
        network = parser.getEdges();
        graphView = new GraphData(network.getGraph(), network.getLines());
        view = new View(graphView);
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
        if (shortestPath != null && shortestPath.size() != 0) view.setShortestPath(shortestPath);
    }

    /**
     * Sets departure station to view
     *
     * @param name station name to set
     */
    public static void setDeparture(String name) {
        view.setDeparture(name);
    }

    /**
     * Sets arrival station to view
     *
     * @param name station name to set
     */
    public static void setArrival(String name) {
        view.setArrival(name);
    }

    /**
     * Sets schedules to view
     *
     * @param v station with schedules to set
     */
    public static void setSchedules(Station v) {
        view.setSchedules(v);
    }

    /**
     * Creates a point defined by the coordinates provided by the user and updates view
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return name of created point
     */
    public static String createPoint(double x, double y) {

        String pointName = network.createPoint(x, y);

        graphView = new GraphData(network.getGraph(), network.getLines());
        view.updateView(graphView);

        return pointName;
    }

    /** Deletes all user points from the graph. */
    public static void deleteUserPoints() {
        network.deleteUserPoints();
    }
}
