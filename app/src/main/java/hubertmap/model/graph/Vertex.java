package hubertmap.model.graph;

/** Represents a labeled vertex in a graph */
public class Vertex {
    private boolean visited = false;
    private float distance = Float.POSITIVE_INFINITY;
    private final String label;

    /**
     * Constructs a new vertex
     *
     * @param label label of the vertex
     */
    Vertex(String label) {
        this.label = label;
    }

    /**
     * @return visited
     */
    protected boolean isVisited() {
        return visited;
    }

    /**
     * sets visited
     *
     * @param visited the new value to set
     */
    protected void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * @return distance
     */
    protected float getDistance() {
        return distance;
    }

    /**
     * @param distance the new distance to set
     */
    protected void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }
}
