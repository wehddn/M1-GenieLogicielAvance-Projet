public class Edge extends Station{
    private int Time;
    private int Distance;
    private Station StartingStation;
    private Station EndingStation;

    public createEdgeForSimpleParser  (Station StartingStation, Station EndingStation, int Line){
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
        this.Line = Line;
    }

    public createEdge((int,int) Time, int Distance, Station StartingStation, Station EndingStation){
        this.Time = Time;
        this.Distance = Distance;
        this.StartingStation = StartingStation;
        this.EndingStation = EndingStation;
    }
    
    public Edge(Station StartingStation, Float StartingStationLatitude, Float StartingStationLongitude, Station EndingStation, Float EndingStationLatitude, Float EndingStationLongitude,  String Line, String Time, Float Distance){
        this.StartingStation = StartingStation;
        this.StartingStationLatitude = StartingStationLatitude;
        this.StartingStationLongitude = StartingStationLongitude;
        this.EndingStation = EndingStation;
        this.EndingStationLatitude = EndingStationLatitude;
        this.EndingStationLongitude = EndingStationLongitude;
        this.Line = Line;
        this.Time = Time;
        this.Distance = Distance;
    }
}