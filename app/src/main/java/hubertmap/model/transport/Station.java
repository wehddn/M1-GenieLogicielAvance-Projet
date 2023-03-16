package hubertmap.model.transport;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private String name;
    private ArrayList<Line> lines;
    private Float latitude;
    private Float longitude;

    public Station(String name, ArrayList<Line> lines, Float latitude, Float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public List<Line> getLines() {
        return lines;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getLongitude() {
        return longitude;
    }
}
