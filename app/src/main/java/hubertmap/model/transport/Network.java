package hubertmap.model.transport;

import java.util.ArrayList;

import hubertmap.model.graph.Edge;
/**
 * This class represents a network of stations and edges between them, forming a transport network.
 */
public class Network{
    private ArrayList<Station> Stations;
    private ArrayList<Edge> Edges;

/**
 * Constructs a new Network with the specified list of stations and edges.
 * If either list is null, an empty list is used instead.
 * @param Stations the list of stations in the network
 * @param Edges the list of edges connecting the stations in the network
 */
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
/**
 * Returns the list of stations in the network.
 * @return the list of stations
 */
    public ArrayList<Station> getStations(){
        return Stations;
    }
/**
 * Returns the list of edges connecting the stations in the network.
 * @return the list of edges
 */
    public ArrayList<Edge> getEdges(){
        return Edges;
    }
}