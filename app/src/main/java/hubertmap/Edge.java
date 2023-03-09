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
}