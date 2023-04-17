package hubertmap.model.transport;

import hubertmap.model.DurationJourney;

public class EdgeTransport {

    private DurationJourney durationJourney;
    private Float Distance;
    private Station StartingStation;
    private Station EndingStation;

    public EdgeTransport(Station StartingStation, Station EndingStation) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }

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

    public Station getStartingStation() {
        return StartingStation;
    }

    public Station getEndingStation() {
        return EndingStation;
    }

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
