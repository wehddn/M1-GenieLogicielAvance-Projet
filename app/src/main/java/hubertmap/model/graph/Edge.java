package hubertmap.model.graph;

import hubertmap.model.Time;
import hubertmap.model.transport.Station;

/**
 * This class represents an edge in a graph, which connects two stations and provides information
 * about the time and distance required to travel between them.
 */
public class Edge {
    private Time Time;
    private Float Distance;
    private Station StartingStation;
    private Station EndingStation;

    /**
     * Constructs a new Edge object.
     *
     * @param StartingStation the starting station of the edge
     * @param EndingStation the ending station of the edge
     */
    public Edge(Station StartingStation, Station EndingStation) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }
    /**
     * Constructs a new Edge object.
     *
     * @param Time the time required to travel the edge
     * @param Distance the distance between the starting and ending stations of the edge
     * @param StartingStation the starting station of the edge
     * @param EndingStation the ending station of the edge
     */
    public Edge(Time Time, Float Distance, Station StartingStation, Station EndingStation) {
        this.Time = Time;
        this.Distance = Distance;
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }
    /**
     * Constructs a new Edge object.
     *
     * @param StartingStation the starting station of the edge
     * @param EndingStation the ending station of the edge
     * @param Time the time required to travel the edge
     * @param Distance the distance between the starting and ending stations of the edge
     */
    public Edge(Station StartingStation, Station EndingStation, Time Time, Float Distance) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
        this.Time = Time;
        this.Distance = Distance;
    }
    /**
     * Returns a string representation of the edge.
     *
     * @return a string representation of the edge
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return StartingStation
                + " - "
                + EndingStation
                + "; time : "
                + Time
                + "; distance : "
                + Distance;
    }

    /*public Edge(Station StartingStation, Float StartingStationLatitude, Float StartingStationLongitude, Station EndingStation, Float EndingStationLatitude, Float EndingStationLongitude,  String Line, String Time, Float Distance){
        this.StartingStation = StartingStation;
        this.StartingStationLatitude = StartingStationLatitude;
        this.StartingStationLongitude = StartingStationLongitude;
        this.EndingStation = EndingStation;
        this.EndingStationLatitude = EndingStationLatitude;
        this.EndingStationLongitude = EndingStationLongitude;
        this.Line = Line;
        this.Time = Time;
        this.Distance = Distance;
    }*/
}
