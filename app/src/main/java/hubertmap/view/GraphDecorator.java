package hubertmap.view;

import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import hubertmap.controller.Controller;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Station;
import java.awt.*;
import java.awt.BasicStroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections15.Transformer;

/**
 * The GraphDecorator class provides methods to decorate a graph with colors, strokes, sizes, and
 * labels for vertices and edges.
 */
public class GraphDecorator {

    /** A map that stores the colors of the lines in the graph. */
    private static HashMap<String, String> lineColors;

    /** The scale used to size the vertices of the graph. */
    private static int scale;

    /** The list of edges in the shortest path of the graph. */
    List<EdgeTransport> shortestPathEdges;

    /** The list of stations in the shortest path of the graph. */
    List<Station> shortestPathStations;

    /** Constructs a new GraphDecorator object with default settings. */
    public GraphDecorator() {
        scale = 0;
        lineColors = createLineColorsMap();
        shortestPathEdges = new ArrayList<>();
        shortestPathStations = new ArrayList<>();
    }

    /**
     * Returns a transformer that used to set vertex colors. Sets specific color if vertex is in
     * shortestPath.
     *
     * @return a Transformer object that maps a Station object to a Paint object representing its
     *     color.
     */
    public Transformer<Station, Paint> vertexColor() {
        Transformer<Station, Paint> vertexColor =
                new Transformer<Station, Paint>() {
                    public Paint transform(Station input) {
                        ArrayList<String> lines = input.getLinesNumbers();
                        if (lines.size() > 1 || lines.size() == 0) return Color.WHITE;
                        else return Color.decode(lineColors.get(lines.get(0)));
                    }
                };
        return vertexColor;
    }

    /**
     * Returns a transformer that used to set edges colors. Sets specific color if edge is in
     * shortestPath.
     *
     * @return a Transformer object that maps an EdgeTransport object to a Paint object representing
     *     its color.
     */
    public Transformer<EdgeTransport, Paint> edgeColor() {
        Transformer<EdgeTransport, Paint> edgeColor =
                new Transformer<EdgeTransport, Paint>() {
                    @Override
                    public Paint transform(EdgeTransport input) {
                        ArrayList<String> lines1 = input.getStartingStation().getLinesNumbers();
                        ArrayList<String> lines2 = input.getEndingStation().getLinesNumbers();
                        lines1.retainAll(lines2);
                        if (lines1.size() != 0) return Color.decode(lineColors.get(lines1.get(0)));
                        else return Color.BLACK;
                    }
                };
        return edgeColor;
    }

    /**
     * Returns a transformer that used to set edges strokes. Sets specific stroke if edge is in
     * shortestPath.
     *
     * @return a Transformer object that maps an EdgeTransport object to a Stroke object
     *     representing its stroke.
     */
    public Transformer<EdgeTransport, Stroke> edgeStroke() {
        return new Transformer<EdgeTransport, Stroke>() {
            @Override
            public Stroke transform(EdgeTransport input) {
                if (shortestPathEdges.contains(input)) return new BasicStroke(6f);
                else return new BasicStroke(1.5f);
            }
        };
    }

    /**
     * Creates a new graph mouse for the graph. Update scale on moves of mouse wheel
     *
     * @return a DefaultModalGraphMouse object for the graph.
     */
    public DefaultModalGraphMouse<Station, EdgeTransport> createGraphMouse() {
        DefaultModalGraphMouse<Station, EdgeTransport> graphMouse =
                new DefaultModalGraphMouse<Station, EdgeTransport>() {
                    @Override
                    public void mouseWheelMoved(MouseWheelEvent e) {
                        super.mouseWheelMoved(e);
                        scale += e.getWheelRotation();
                    }
                };
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        return graphMouse;
    }

    /**
     * Returns a transformer that used to set vertex sizes. Dynamically changes the size depending
     * on the scale
     *
     * @return a Transformer object that maps a Station object to a Shape object representing its
     *     size.
     */
    public Transformer<Station, Shape> vertexSize() {
        Transformer<Station, Shape> vertexSize =
                new Transformer<Station, Shape>() {
                    @Override
                    public Shape transform(Station input) {
                        if (shortestPathStations.contains(input))
                            return new Ellipse2D.Float(-5, -5, 10, 10);
                        else {
                            if (scale > 15) return new Ellipse2D.Float(-5, -5, 10, 10);
                            else {
                                int value = scale / 3;
                                return new Ellipse2D.Float(-value, -value, value * 2, value * 2);
                            }
                        }
                    }
                };
        return vertexSize;
    }

    /**
     * Returns a labeler that used to set vertex labels.
     *
     * @return a ToStringLabeller object that maps a Station object to a String object representing
     *     its label.
     */
    public ToStringLabeller<Station> toStringLabeller() {
        ToStringLabeller<Station> labeller =
                new ToStringLabeller<>() {
                    @Override
                    public String transform(Station v) {
                        if (shortestPathStations.contains(v)) return super.transform(v);
                        else {
                            if (scale < 20) return "";
                            else return super.transform(v);
                        }
                    }
                    ;
                };
        return labeller;
    }

    /**
     * Creates a map that stores the colors of the lines in the graph.
     *
     * @return a HashMap object that stores the colors of the lines in the graph.
     */
    private HashMap<String, String> createLineColorsMap() {
        lineColors = new HashMap<String, String>();
        lineColors.put("1", "#FFCE00");
        lineColors.put("2", "#0064B0");
        lineColors.put("3", "#9F9825");
        lineColors.put("3B", "#98D4E2");
        lineColors.put("4", "#C04191");
        lineColors.put("5", "#F28E42");
        lineColors.put("6", "#83C491");
        lineColors.put("7", "#F3A4BA");
        lineColors.put("7B", "#83C491");
        lineColors.put("8", "#CEADD2");
        lineColors.put("9", "#D5C900");
        lineColors.put("10", "#E3B32A");
        lineColors.put("11", "#8D5E2A");
        lineColors.put("12", "#00814F");
        lineColors.put("13", "#98D4E2");
        lineColors.put("14", "#662483");
        lineColors.put("15", "#B90845");
        lineColors.put("16", "#F3A4BA");
        lineColors.put("17", "#D5C900");
        lineColors.put("18", "#00A88F");
        return lineColors;
    }

    /**
     * Sets the shortest path of the graph.
     *
     * @param shortestPath the list of edges in the shortest path of the graph.
     */
    public void setShortestPath(List<EdgeTransport> shortestPath) {
        if (shortestPath != null) {
            this.shortestPathEdges = shortestPath;
            shortestPathStations.clear();
            for (EdgeTransport edgeTransport : shortestPath) {
                Station station1 = edgeTransport.getStartingStation();
                Station station2 = edgeTransport.getEndingStation();
                shortestPathStations.add(station1);
                shortestPathStations.add(station2);
            }
            Set<Station> set = new HashSet<>(shortestPathStations);
            shortestPathStations.clear();
            shortestPathStations.addAll(set);
        }
    }

    /**
     * Returns a new GraphMouseListener for Stations, which listens for clicks on the graph, and
     * calls the setSchedules method of the Controller class with the clicked Station.
     *
     * @return a new GraphMouseListener object for Stations.
     */
    public GraphMouseListener<Station> graphMouseListener() {
        return new GraphMouseListener<Station>() {

            @Override
            public void graphClicked(Station v, MouseEvent me) {
                // TODO Auto-generated method stub
                Controller.setSchedules(v);
            }

            @Override
            public void graphPressed(Station v, MouseEvent me) {
                // TODO Auto-generated method stub

            }

            @Override
            public void graphReleased(Station v, MouseEvent me) {
                // TODO Auto-generated method stub

            }
        };
    }
}
