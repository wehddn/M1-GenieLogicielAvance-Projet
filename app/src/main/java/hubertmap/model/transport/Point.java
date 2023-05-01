package hubertmap.model.transport;

public class Point extends VertexTransport {
    public Point(String name, double d, double e) {
        super(name, Float.valueOf(String.valueOf(d)), Float.valueOf(String.valueOf(e)));
    }
}
