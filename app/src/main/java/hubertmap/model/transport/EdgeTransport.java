package hubertmap.model.transport;

import hubertmap.model.Time;

public class EdgeTransport {

    private Time time;
    private Float Distance;
    private Station StartingStation;
    private Station EndingStation;

    public EdgeTransport(Station StartingStation, Station EndingStation) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }

    public EdgeTransport(
            Time time, Float Distance, Station StartingStation, Station EndingStation) {
        this.time = time;
        this.Distance = Distance;
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }

    public EdgeTransport(
            Station StartingStation, Station EndingStation, Time time, Float Distance) {
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
        this.time = time;
        this.Distance = Distance;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return StartingStation
                + " - "
                + EndingStation
                + "; time : "
                + time
                + "; distance : "
                + Distance;
    }

    public Station getStartingStation() {
        return StartingStation;
    }

    public Station getEndingStation() {
        return EndingStation;
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
