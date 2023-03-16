package hubertmap.model.transport;

public class Line {
    private String name;
    private Station firstStation;
    private Station lastStation;

    public Line(String name, Station firstStation, Station lastStation){
        this.name = name;
        this.firstStation = firstStation;
        this.lastStation = lastStation;
    }

    public String getName(){
        return name;
    }

    public Station getFirstStation(){
        return firstStation;
    }

    public Station getLastStation(){
        return lastStation;
    }
}