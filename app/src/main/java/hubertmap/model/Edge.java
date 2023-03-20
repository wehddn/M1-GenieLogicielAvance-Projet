package hubertmap.model;
public class Edge{
    private Time Time;
    private Float Distance;
    private Station StartingStation;
    private Station EndingStation;

    public Edge (Station StartingStation, Station EndingStation){
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }

    public Edge(Time Time, Float Distance, Station StartingStation, Station EndingStation){
        this.Time = Time;
        this.Distance = Distance;
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }

    public Edge(Station StartingStation, Station EndingStation, Time Time, Float Distance){
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
        this.Time = Time;
        this.Distance = Distance;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return StartingStation + " - " + EndingStation + "; time : " + Time + "; distance : " + Distance;
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