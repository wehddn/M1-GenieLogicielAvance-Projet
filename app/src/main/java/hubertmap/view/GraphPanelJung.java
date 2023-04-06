package hubertmap.view;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.SparseGraph;
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
import java.util.ArrayList;
import javax.swing.*;

class GraphPanelJung extends JPanel {

    private int panelWidth, panelHeight;
    private edu.uci.ics.jung.graph.Graph<Node, Integer> graph;
    private double longitudeScale;
    private double latitudeScale;
    private double minimumLongitude;
    private double maximumLatitude;
    Layout<Node, Integer> layout;
    ScalingControl scaler;
    VisualizationViewer<Node, Integer> vv;
    private ArrayList<EdgeTransport> edges;

    public GraphPanelJung(ArrayList<EdgeTransport> edges) {

        this.edges = edges;

        panelWidth = 600;
        panelHeight = 600;

        createGraph();

        layout = new CircleLayout<Node, Integer>(graph);
        layout.setSize(new Dimension(panelWidth, panelHeight));
        vv = new VisualizationViewer<Node, Integer>(layout);

        setUpCoords();

        DefaultModalGraphMouse<Node, Integer> graphMouse =
                new DefaultModalGraphMouse<Node, Integer>();
        graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(graphMouse);

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());

        vv.getRenderContext().setEdgeShapeTransformer(EdgeShape.line(graph));

        vv.setPreferredSize(new Dimension(panelWidth, panelHeight));

        this.add(vv);
    }

    private void createGraph() {
        graph = new SparseGraph<Node, Integer>();

        minimumLongitude = 180;
        double maximumLongitude = -180;
        double minimumLatitude = 90;
        maximumLatitude = -90;

        int i = 0;
        for (EdgeTransport edge : edges) {
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

            Node node1 = new Node(s1.getName(), s1.getX(), s1.getY());
            Node node2 = new Node(s2.getName(), s2.getX(), s2.getY());
            graph.addEdge(i, node1, node2);
            i++;
        }

        double longitudeDiff = maximumLongitude - minimumLongitude;
        double latitudeDiff = maximumLatitude - minimumLatitude;

        longitudeScale = panelWidth / longitudeDiff;
        latitudeScale = panelHeight / latitudeDiff;
    }

    void setUpCoords() {
        for (Node node : graph.getVertices()) {
            int nodex = (int) Math.round((node.getX() - minimumLongitude) * longitudeScale);
            int nodey = (int) Math.round((maximumLatitude - node.getY()) * latitudeScale);
            layout.setLocation(node, new Point2D.Double(nodex, nodey));
        }
    }

    class Node {

        String name;
        double x;
        double y;

        public Node(String name, double d, double e) {
            this.name = name;
            this.x = d;
            this.y = e;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public boolean equals(Node node) {
            if (this.getName() == node.getName()) return true;
            else return false;
        }

        private String getName() {
            return name;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return name;
        }
    }
}
