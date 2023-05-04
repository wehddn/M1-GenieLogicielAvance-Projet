package hubertmap.view;

import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Line;
import hubertmap.model.transport.Station;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    JPanel generalPanel;

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
        setPathDetails(shortestPath);
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

        String start = searchPanel.DepartureGetText();
        String end = searchPanel.ArrivalGetText();

        if ((start.equals("departure : station or coordinates")) || (start.equals(""))) {
            searchPanel.setDeparture(v.toString());
        } else {
            if ((end.equals("arrival : station or coordinates")) || (end.equals(""))) {
                searchPanel.setArrival(v.toString());
            }
        }

        schedulesPanel = new SchedulesPanel(v, lines);
        leftPanel.add(schedulesPanel);

        leftPanel.revalidate();
    }

    /**
     * Creates panel with details of path
     *
     * @param shortestPath shortes path to output
     */
    public void setPathDetails(List<EdgeTransport> shortestPath) {
        if (generalPanel != null) leftPanel.remove(generalPanel);
        if (schedulesPanel != null) leftPanel.remove(schedulesPanel);

        JPanel generalPanel = new JPanel();
        generalPanel.setLayout(new BoxLayout(generalPanel, BoxLayout.Y_AXIS));
        generalPanel.setLayout(new GridLayout(0, 1, 5, 5));

        ArrayList<String[]> segments = getSegments(shortestPath);
        for (int i = 0; i < segments.size(); i += 2) {
            JPanel pathPanel =
                    createPathPanel(segments.get(i)[0], segments.get(i)[1], segments.get(i + 1)[1]);

            generalPanel.add(pathPanel);
        }

        leftPanel.add(generalPanel);

        leftPanel.revalidate();
    }

    /**
     * Creates a panel with details of path between 2 stations
     *
     * @param line line number of given path
     * @param stationName1 first station of path
     * @param stationName2 last station of path
     */
    private JPanel createPathPanel(String lineName, String stationName1, String stationName2) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.X_AXIS));

        JPanel way = new JPanel();
        way.setLayout(new BoxLayout(way, BoxLayout.Y_AXIS));
        way.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.BLACK));

        JPanel start = new JPanel();
        start.setLayout(new BorderLayout());
        start.setPreferredSize(new Dimension(240, 20));
        JLabel start1L = new JLabel(stationName1);
        start.add(start1L, BorderLayout.NORTH);

        JPanel finish = new JPanel();
        finish.setLayout(new BorderLayout());
        finish.setPreferredSize(new Dimension(240, 20));
        JLabel finishL = new JLabel(stationName2);
        finish.add(finishL, BorderLayout.SOUTH);
        way.add(start);
        way.add(finish);

        JPanel line = new JPanel();
        JLabel lineL = new JLabel(lineName);
        lineL.setForeground(Color.WHITE);
        line.setPreferredSize(new Dimension(15, 40));
        line.add(lineL);
        Color color = Color.decode(LineColor.getColor(lineName));
        line.setBackground(color);
        line.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));

        sectionPanel.add(line);
        sectionPanel.add(way);

        return sectionPanel;
    }

    /**
     * Divides EdgeTransport into an array of line - station values
     *
     * @param shortestPath list to divide
     * @return array of line - station values
     */
    private ArrayList<String[]> getSegments(List<EdgeTransport> shortestPath) {
        String currentLine = shortestPath.get(0).getLineName();
        String lastStationOnSameLine = null;
        ArrayList<String[]> segments = new ArrayList<>();
        for (int i = 0; i < shortestPath.size(); i++) {
            EdgeTransport edge = shortestPath.get(i);
            if (i == 0)
                segments.add(
                        new String[] {edge.getLineName(), edge.getStartingStation().getName()});
            if (!edge.getLineName().equals(currentLine)) {
                segments.add(new String[] {currentLine, lastStationOnSameLine});
                segments.add(
                        new String[] {edge.getLineName(), edge.getStartingStation().getName()});
                currentLine = edge.getLineName();
            }
            lastStationOnSameLine = edge.getEndingStation().getName();
        }
        segments.add(new String[] {currentLine, lastStationOnSameLine});
        return segments;
    }

    /* Updates view with new data
     *
     * @param graphView GraphData to set in view
     */
    public void updateView(GraphData graphView) {
        frame.getContentPane().remove(panel);
        panel = createPanel(graphView);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocusInWindow();
    }
}
