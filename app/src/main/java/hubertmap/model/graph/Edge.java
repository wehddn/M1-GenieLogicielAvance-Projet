package hubertmap.model.graph;

public class Edge {
    private float weight;
    private Vertex v1;
    private Vertex v2;

    public Edge(float weight, Vertex v1, Vertex v2) {}

    public float getWeight() {
      return weight;
    }

    public Vertex getV1() {
      return v1;
    }

    public Vertex getV2() {
      return v2;
    }
}
