package hubertmap.model.transport;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private ArrayList<Station> stations;

    public Network(ArrayList<Station> stations){
        this.stations = stations;
    }

    public List<Station> getStations(){
        return stations;
    }
}