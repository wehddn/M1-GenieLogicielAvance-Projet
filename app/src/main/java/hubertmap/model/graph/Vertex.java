package hubertmap.model.graph;

public class Vertex {
  private boolean visited = false;
  private float distance = Float.POSITIVE_INFINITY;
  private final String label;

  Vertex(String label) {
    this.label = label;
  }

  public boolean isVisited() {
    return visited;
  }

  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public float getDistance() {
    return distance;
  }

  public void setDistance(float distance) {
    this.distance = distance;
  }

  public String getLabel() {
    return label;
  }

}
