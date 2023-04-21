package hubertmap.model.transport;

import hubertmap.model.DurationJourney;

/**
 * The EdgeTransport class represents a transport edge between two stations. It contains information
 * about the journey duration, distance, and the starting and ending stations.
 */
public class EdgeTransport {

    private DurationJourney durationJourney;
    private Float Distance;
    private Station StartingStation;
    private Station EndingStation;

    /**
     * Creates a new EdgeTransport object with the given starting and ending stations.
     *
     * @param StartingStation the starting station of the transport edge
     * @param EndingStation the ending station of the transport edge
     */
    public EdgeTransport(Station StartingStation, Station EndingStation) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }
    /**
     * Creates a new EdgeTransport object with the given duration, distance, and starting and ending
     * stations.
     *
     * @param durationJourney the duration of the journey between the starting and ending stations
     * @param Distance the distance between the starting and ending stations
     * @param StartingStation the starting station of the transport edge
     * @param EndingStation the ending station of the transport edge
     */
    public EdgeTransport(
            DurationJourney durationJourney,
            Float Distance,
            Station StartingStation,
            Station EndingStation) {
        this.durationJourney = durationJourney;
        this.Distance = Distance;
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }

    /**
     * Creates a new EdgeTransport object with the given starting and ending stations, duration, and
     * distance.
     *
     * @param StartingStation the starting station of the transport edge
     * @param EndingStation the ending station of the transport edge
     * @param durationJourney the duration of the journey between the starting and ending stations
     * @param Distance the distance between the starting and ending stations
     */
    public EdgeTransport(
            Station StartingStation,
            Station EndingStation,
            DurationJourney durationJourney,
            Float Distance) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
        this.durationJourney = durationJourney;
        this.Distance = Distance;
    }

    /**
     * Returns a string representation of the EdgeTransport object, including the starting and
     * ending stations, journey duration, and distance.
     *
     * @return a string representation of the EdgeTransport object
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return StartingStation
                + " - "
                + EndingStation
                + "; durationJourney : "
                + durationJourney
                + "; distance : "
                + Distance;
    }

    /**
     * Returns the starting station of the transport edge.
     *
     * @return the starting station of the transport edge
     */
    public Station getStartingStation() {
        return StartingStation;
    }
    /**
     * Returns the ending station of the transport edge.
     *
     * @return the ending station of the transport edge
     */
    public Station getEndingStation() {
        return EndingStation;
    }
    /**
     * Returns the distance between the starting and ending stations.
     *
     * @return the distance between the starting and ending stations
     */
    public Float getDistance() {
        return Distance;
    }

    /*public Edge(Station StartingStation, Float StartingStationLatitude, Float StartingStationLongitude, Station EndingStation, Float EndingStationLatitude, Float EndingStationLongitude,  String Line, String durationJourney, Float Distance){
        this.StartingStation = StartingStation;
        this.StartingStationLatitude = StartingStationLatitude;
        this.StartingStationLongitude = StartingStationLongitude;
        this.EndingStation = EndingStation;
        this.EndingStationLatitude = EndingStationLatitude;
        this.EndingStationLongitude = EndingStationLongitude;
        this.Line = Line;
        this.durationJourney = durationJourney;
        this.Distance = Distance;
    }*/

}
