package hubertmap.model;

import java.util.ArrayList;

public class Network{
    private ArrayList<Station> Stations;
    private ArrayList<Edge> Edges;

    public Network(ArrayList<Station> Stations, ArrayList<Edge> Edges){
        if(Stations != null){
            this.Stations = Stations;
        }
        else{
            this.Stations = new ArrayList<Station>();
        }
        if(Edges != null){
            this.Edges = Edges;
        }
        else{
            this.Edges = new ArrayList<Edge>();
        }
    }

    public ArrayList<Station> getStations(){
        return Stations;
    }

    public ArrayList<Edge> getEdges(){
        return Edges;
    }
}