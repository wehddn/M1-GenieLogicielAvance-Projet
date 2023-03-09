package hubertmap.model.transport;

public class Line {
    private String name;
    private String terminalStationDeparture;
    private String terminalStationArrival;

    public Line(String name, String terminalStationDeparture, String terminalStationArrival){
        this.name = name;
        this.terminalStationDeparture = terminalStationDeparture;
        this.terminalStationArrival = terminalStationArrival;
    }

    public String getName(){
        return name;
    }

    public String getterminalStationDeparture(){
        return terminalStationDeparture;
    }

    public String getterminalStationArrival(){
        return terminalStationArrival;
    }
}