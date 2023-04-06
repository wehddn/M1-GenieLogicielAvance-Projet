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
import hubertmap.model.transport.Network;
import hubertmap.model.transport.Station;
import java.awt.*;
import java.awt.geom.Point2D;
import javax.swing.*;

class GraphPanelJung extends JPanel {

    private int panelWidth, panelHeight;
    Layout<Station, EdgeTransport> layout;
    ScalingControl scaler;
    VisualizationViewer<Station, EdgeTransport> vv;
    Graph<Station, EdgeTransport> graph;
    Network network;

    public GraphPanelJung(Network network) {

        this.network = network;
        this.graph = network.getGraph();

        panelWidth = 600;
        panelHeight = 600;

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

    private void setUpCoords() {
        double minimumLongitude = network.getMinimumLongitude();
        double maximumLongitude = network.getMaximumLongitude();
        double minimumLatitude = network.getMinimumLatitude();
        double maximumLatitude = network.getMaximumLatitude();

        double longitudeDiff = maximumLongitude - minimumLongitude;
        double latitudeDiff = maximumLatitude - minimumLatitude;

        double longitudeScale = panelWidth / longitudeDiff;
        double latitudeScale = panelHeight / latitudeDiff;

        for (Station station : graph.getVertices()) {
            int stationx = (int) Math.round((station.getX() - minimumLongitude) * longitudeScale);
            int stationy = (int) Math.round((maximumLatitude - station.getY()) * latitudeScale);
            layout.setLocation(station, new Point2D.Double(stationx, stationy));
        }
    }
}
