package hubertmap.model.graph;

/** Positively weighted oriented edge between two vertices */
public class Edge {
    private float weight;
    private Vertex v1;
    private Vertex v2;

    /**
     * constructs a new oriented edge from v1 to v2
     *
     * @param weight positive weight
     * @param v1 origin vertex
     * @param v2 end vertex
     * @throws IllegalArgumentException if the weight is negative
     */
    public Edge(float weight, Vertex v1, Vertex v2) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
        this.v1 = v1;
        this.v2 = v2;
    }

    /**
     * constructs a new oriented edge with new vertices
     *
     * @param weight positive weight
     * @param label1 label of origin vertex
     * @param label2 label of end vertex
     * @throws IllegalArgumentException if the weight is negative
     */
    public Edge(float weight, String label1, String label2) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
        this.v1 = new Vertex(label1);
        this.v2 = new Vertex(label2);
    }

    /**
     * @return weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * @return origin vertex
     */
    protected Vertex getV1() {
        return v1;
    }

    /**
     * @return end vertex
     */
    protected Vertex getV2() {
        return v2;
    }
}
