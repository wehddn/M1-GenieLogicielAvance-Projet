package hubertmap.model.transport;

import java.util.ArrayList;

/** The Line class represents a transportation line that connects two terminal stations. */
public class Line {
    private String lineName;
    private Station terminalStationDeparture;
    private Station terminalStationArrival;
    private ArrayList<Station> allStation;

    // public Line(String lineName, Station terminalStationDeparture, Station
    // terminalStationArrival) {
    //     this.allStation = new ArrayList<>();
    //     this.lineName = lineName;
    //     this.terminalStationDeparture = terminalStationDeparture;
    //     this.terminalStationArrival = terminalStationArrival;
    // }
    public Line(String lineName, Station station1, Station station2) {
        this.allStation = new ArrayList<>();
        this.lineName = lineName;
        allStation.add(station1);
        allStation.add(station2);
    }

    public String getLineName() {
        return lineName;
    }

    public Station getTerminalStationDeparture() {
        return terminalStationDeparture;
    }

    public Station getTerminalStationArrival() {
        return terminalStationArrival;
    }

    public void addStation(Station station1) {
        for (int i = 0; i < allStation.size(); i++) {
            if (station1.equals(allStation.get(i))) {
                return;
            }
        }
        allStation.add(station1);
    }

    public ArrayList getAllStation() {
        return allStation;
    }
}
