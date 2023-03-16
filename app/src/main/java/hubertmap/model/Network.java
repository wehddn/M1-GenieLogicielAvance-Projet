package hubertmap.model;
public class Network extends Station, Edge{
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