package hubertmap.model.transport;

import hubertmap.model.Time;
import java.util.ArrayList;

/** The Line class represents a transportation line that connects two terminal stations. */
public class Line {
    private String name;
    private Station terminalStationDeparture;
    private Station terminalStationArrival;
    public ArrayList<Time> starts;
    public ArrayList<Station> allStations;

    public Line(
            String name,
            Station terminalStationDeparture,
            Station terminalStationArrival,
            Time start) {
        this.name = name;
        this.terminalStationDeparture = terminalStationDeparture;
        this.terminalStationArrival = terminalStationArrival;
        this.starts = new ArrayList<>();
        this.allStations = new ArrayList<>();
        this.starts.add(start);
    }

    public Line(String name, Station terminaleStationDeparture) {
        this.name = name;
        this.terminalStationDeparture = terminaleStationDeparture;
        terminalStationArrival = null;
        this.starts = new ArrayList<>();
        this.allStations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Station getTerminalStationDeparture() {
        return terminalStationDeparture;
    }

    public Station getTerminalStationArrival() {
        return terminalStationArrival;
    }

    public void addStationsIfNotAlreadyExist(Station newStation) {
        for (Station station : allStations) {
            if (station.getName().equals(newStation.getName())) {
                return;
            }
        }
        allStations.add(newStation);
    }

    public void setTerminalStationArrival(Station terminalStationArrival) {
        this.terminalStationArrival = terminalStationArrival;
    }

    public void addStart(Time start) {
        starts.add(start);
    }

    public String toString() {
        return String.format(
                "%s, départ : %s, terminus : %s, horaires : %s",
                name, terminalStationDeparture, terminalStationArrival, starts);
    }
}
