package hubertmap.view;

import edu.uci.ics.jung.graph.Graph;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Station;
import javax.swing.JFrame;

/**
 * The View class represents the graphical user interface of the application, and contains a Panel
 * instance for displaying the main content of the GUI.
 */
public class View {

    /** The Panel instance that displays the main content of the GUI. */
    private Panel panel;

    /**
     * Constructs a new View instance and initializes its components.
     *
     * <p>This constructor creates a JFrame window and adds the Panel to it.
     *
     * @param graph the list of edges to be displayed
     */
    public View(Graph<Station, EdgeTransport> graph) {
        panel = new Panel(graph);

        JFrame frame = new JFrame("Hubertmap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Returns the Panel instance of the View.
     *
     * @return the Panel instance of the View
     */
    public Panel getPanel() {
        return panel;
    }
}
