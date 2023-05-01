package hubertmap.view;

import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Line;
import hubertmap.model.transport.Station;
import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * The View class represents the graphical user interface of the application, and contains a Panel
 * instance for displaying the main content of the GUI.
 */
public class View {

    /** The Panel instance that displays the main content of the GUI. */
    private JPanel panel;

    /** A GraphPanelJung object for drawing the graph using the JUNG library. */
    GraphPanel graphPanel;

    JPanel leftPanel;

    SchedulesPanel schedulesPanel;

    private HashMap<String, Line> lines;

    private JFrame frame;

    SearchPanel searchPanel;

    /**
     * Constructs a new View instance and initializes its components. Creates a JFrame window and
     * adds the Panel to it.
     *
     * @param graphView the the NetworkData with graph and informations to be displayed
     */
    public View(GraphData graphView) {
        panel = createPanel(graphView);

        frame = new JFrame("Hubertmap");
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
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        graphPanel = new GraphPanel(graphView);
        graphPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        this.lines = graphView.getLines();

        leftPanel = new JPanel(new BorderLayout());

        searchPanel = new SearchPanel(graphView);

        leftPanel.add(searchPanel, BorderLayout.NORTH);

        panel.add(leftPanel);

        panel.add(graphPanel);

        return panel;
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
     * Sets the name of the departure station to be displayed in the text area.
     *
     * @param name the name of the departure station to be displayed.
     */
    public void setDeparture(String name) {
        searchPanel.setDeparture(name);
    }

    /**
     * Sets the name of the arrival station to be displayed in the text area.
     *
     * @param name the name of the arrival station to be displayed.
     */
    public void setArrival(String name) {
        searchPanel.setArrival(name);
    }

    /**
     * Sets the schedules panel to display the schedules for the given station.
     *
     * @param v the station for which the schedules will be displayed.
     */
    public void setSchedules(Station v) {
        if (schedulesPanel != null) leftPanel.remove(schedulesPanel);

        schedulesPanel = new SchedulesPanel(v, lines);
        leftPanel.add(schedulesPanel);

        leftPanel.revalidate();
    }

    public void updateView(GraphData graphView) {
        frame.getContentPane().remove(panel);
        panel = createPanel(graphView);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
}
