package hubertmap.view;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Station;
import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.*;

class GraphPanelJung extends JPanel {

    private int panelWidth, panelHeight;
    private double longitudeScale;
    private double latitudeScale;
    private double minimumLongitude;
    private double maximumLatitude;
    Layout<Station, EdgeTransport> layout;
    ScalingControl scaler;
    VisualizationViewer<Station, EdgeTransport> vv;
    Graph<Station, EdgeTransport> graph;

    public GraphPanelJung(Graph<Station, EdgeTransport> graph) {

        this.graph = graph;

        panelWidth = 600;
        panelHeight = 600;

        createGraph();

        layout = new CircleLayout<Station, EdgeTransport>(graph);
        layout.setSize(new Dimension(panelWidth, panelHeight));
        vv = new VisualizationViewer<Station, EdgeTransport>(layout);

        setUpCoords();

        DefaultModalGraphMouse<Station, EdgeTransport> graphMouse =
                new DefaultModalGraphMouse<Station, EdgeTransport>();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(graphMouse);

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

        vv.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(graph));

        vv.setPreferredSize(new Dimension(panelWidth, panelHeight));

        this.add(vv);
    }

    private void createGraph() {

        minimumLongitude = 180;
        double maximumLongitude = -180;
        double minimumLatitude = 90;
        maximumLatitude = -90;

        for (EdgeTransport edge : graph.getEdges()) {
            Station s1 = edge.getStartingStation();
            Station s2 = edge.getEndingStation();

            if (s1.getX() < minimumLongitude) minimumLongitude = s1.getX();
            if (s1.getX() > maximumLongitude) maximumLongitude = s1.getX();

            if (s1.getY() < minimumLatitude) minimumLatitude = s1.getY();
            if (s1.getY() > maximumLatitude) maximumLatitude = s1.getY();

            if (s2.getX() < minimumLongitude) minimumLongitude = s2.getX();
            if (s2.getX() > maximumLongitude) maximumLongitude = s2.getX();

            if (s2.getY() < minimumLatitude) minimumLatitude = s2.getY();
            if (s2.getY() > maximumLatitude) maximumLatitude = s2.getY();
        }

        double longitudeDiff = maximumLongitude - minimumLongitude;
        double latitudeDiff = maximumLatitude - minimumLatitude;

        longitudeScale = panelWidth / longitudeDiff;
        latitudeScale = panelHeight / latitudeDiff;
    }

    void setUpCoords() {
        for (Station station : graph.getVertices()) {
            int stationx = (int) Math.round((station.getX() - minimumLongitude) * longitudeScale);
            int stationy = (int) Math.round((maximumLatitude - station.getY()) * latitudeScale);
            layout.setLocation(station, new Point2D.Double(stationx, stationy));
        }
    }
}
