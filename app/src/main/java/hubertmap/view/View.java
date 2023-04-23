package hubertmap.view;

import hubertmap.controller.Controller;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Station;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The View class represents the graphical user interface of the application, and contains a Panel
 * instance for displaying the main content of the GUI.
 */
public class View {

    /** The Panel instance that displays the main content of the GUI. */
    private JPanel panel;

    /** A List object to hold names of station for search. */
    private List<String> stationsNames;

    /** A collection mapping station names without accents to their actual names */
    private HashMap<String, String> actualStationsNames;

    /** A GraphPanelJung object for drawing the graph using the JUNG library. */
    GraphPanel graphPanel;

    /** TextArea with autocompletion to search start station */
    TextAreaDemo textAreaStationStart;

    /** TextArea with autocompletion to search end station */
    TextAreaDemo textAreaStationEnd;

    /**
     * Constructs a new View instance and initializes its components. Creates a JFrame window and
     * adds the Panel to it.
     *
     * @param graphView the the NetworkData with graph and informations to be displayed
     */
    public View(GraphData graphView) {
        panel = createPanel(graphView);

        JFrame frame = new JFrame("Hubertmap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }

    /**
     * Creates a panel containing a graph and a search panel.
     *
     * @param graphView the NetworkData object containing the graph data
     * @return the created panel
     */
    private JPanel createPanel(GraphData graphView) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        graphPanel = new GraphPanel(graphView);

        setData(graphView.getVertices());
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));

        textAreaStationStart = new TextAreaDemo(stationsNames);
        textAreaStationStart.setText("Departure");
        textAreaStationEnd = new TextAreaDemo(stationsNames);
        textAreaStationEnd.setText("Arrival");

        JButton search = new JButton("Search");

        search.addActionListener(search());

        searchPanel.add(textAreaStationStart);
        searchPanel.add(textAreaStationEnd);
        searchPanel.add(search);

        panel.add(searchPanel);

        panel.add(graphPanel);

        return panel;
    }

    /**
     * Returns an ActionListener that executes a search for the shortest path between two stations.
     *
     * @return ActionListener that executes a search for the shortest path
     */
    private ActionListener search() {
        ActionListener al =
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String station1Name = textAreaStationStart.getValue();
                        String station2Name = textAreaStationEnd.getValue();
                        String s1 = actualStationsNames.get(station1Name);
                        String s2 = actualStationsNames.get(station2Name);
                        if (s1 != null) {
                            station1Name = s1;
                        }
                        if (s2 != null) {
                            station2Name = s2;
                        }
                        Controller.setShortestPath(station1Name, station2Name);
                    }
                };
        return al;
    }

    /**
     * Convert Collection of station to ArrayList and initialise stationsNames list.
     *
     * @param stations Collection of station used for search
     */
    private void setData(Collection<Station> stations) {
        stationsNames = new ArrayList<>();
        actualStationsNames = new HashMap<>();
        for (Station station : stations) {
            String n1 = station.getName().toLowerCase();
            String n2 = stripAccents(n1);
            stationsNames.add(n1);
            if (!(n1.equals(n2))) {
                stationsNames.add(n2);
                actualStationsNames.put(n2, n1);
            }
        }
        Collections.sort(stationsNames);
    }

    /**
     * Sets the list of edges in the View that make up the shortest path
     *
     * @param shortestPath List of edges to set in View
     */
    public void setShortestPath(List<EdgeTransport> shortestPath) {
        graphPanel.getDecorator().setShortestPath(shortestPath);
        graphPanel.repaint();
    }

    /**
     * @param s a character string
     * @return s with accents replaced with ascii characters
     */
    private String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public void setDeparture(String name) {
        textAreaStationStart.setText(name);
    }

    public void setArrival(String name) {
        textAreaStationEnd.setText(name);
    }
}
