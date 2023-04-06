package hubertmap.view;

import java.awt.*;
import java.awt.image.*;
import java.util.ArrayList;
import javax.swing.*;

class GraphPanelCustom extends JPanel {

    private BufferedImage image;
    private Graphics g;
    private int panelWidth, panelHeight;
    private Graph graph;
    private int radius;
    private double longitudeScale;
    private double latitudeScale;
    private double minimumLongitude;
    private double maximumLatitude;

    public GraphPanelCustom() {

        graph = new Graph();

        panelWidth = 400;
        panelHeight = 400;
        radius = 10;

        double moveValue = 0.001;

        minimumLongitude = graph.getMinimumLongitude() - moveValue;
        double maximumLongitude = graph.getMaximumLongitude() + moveValue;
        double minimumLatitude = graph.getMinimumLatitude() - moveValue;
        maximumLatitude = graph.getMaximumLatitude() + moveValue;

        double longitudeDiff = maximumLongitude - minimumLongitude;
        double latitudeDiff = maximumLatitude - minimumLatitude;

        longitudeScale = panelWidth / longitudeDiff;
        latitudeScale = panelHeight / latitudeDiff;

        this.setPreferredSize(new Dimension(panelWidth, panelHeight));

        image = new BufferedImage(panelWidth, panelHeight, BufferedImage.TYPE_INT_RGB);
        g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, panelWidth, panelHeight);

        drawGraph();
    }

    void drawGraph() {
        for (Edge edge : graph.getEdges()) {
            Node nodel = edge.getNodel();
            Node noder = edge.getNoder();
            int lineRadius = radius / 2;

            int nodelx = (int) Math.round((nodel.getX() - minimumLongitude) * longitudeScale);
            int nodely = (int) Math.round((maximumLatitude - nodel.getY()) * latitudeScale);
            int noderx = (int) Math.round((noder.getX() - minimumLongitude) * longitudeScale);
            int nodery = (int) Math.round((maximumLatitude - noder.getY()) * latitudeScale);

            g.setColor(Color.black);

            g.drawLine(
                    nodelx + lineRadius,
                    nodely + lineRadius,
                    noderx + lineRadius,
                    nodery + lineRadius);

            g.fillOval(nodelx - 3, nodely - 3, radius + 6, radius + 6);

            g.fillOval(noderx - 3, nodery - 3, radius + 6, radius + 6);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}

class Graph {

    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private double minimumLongitude;
    private double maximumLongitude;
    private double minimumLatitude;
    private double maximumLatitude;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        minimumLongitude = 2.310588789873988;
        maximumLongitude = 2.3220315426557194;
        minimumLatitude = 48.85766922956403;
        maximumLatitude = 48.86652125193945;

        Node node1 = new Node("La Tour-Maubourg", 2.310588789873988, 48.85766922956403);
        Node node2 = new Node("Invalides", 2.314996938416133, 48.86059945384315);
        Node node3 = new Node("Concorde", 2.3220315426557194, 48.86652125193945);
        nodes.add(node1);
        nodes.add(node2);
        edges.add(new Edge(node1, node2));

        nodes.add(node3);
        edges.add(new Edge(node2, node3));
    }

    public double getMinimumLatitude() {
        return minimumLatitude;
    }

    public double getMaximumLongitude() {
        return maximumLongitude;
    }

    public double getMinimumLongitude() {
        return minimumLongitude;
    }

    public double getMaximumLatitude() {
        return maximumLatitude;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
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
}

class Edge {

    private Node nodel;
    private Node noder;

    public Edge(Node node1, Node node2) {
        this.nodel = node1;
        this.noder = node2;
    }

    public Node getNoder() {
        return noder;
    }

    public Node getNodel() {
        return nodel;
    }
}
