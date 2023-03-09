public class Station {
    private String Name;
    private ArrayList<Line> Lines;
    private Float x;
    private Float y;

    public Station(String Name, ArrayList<int> allLines, Float x, Float y){
        this.Name = Name;
        this.x = x;
        this.y = y;
        if(allLines != null){
            this.allLines = Lines;
        }
        else{
            this.allLines = new ArrayList<int>();
        }
    }

    public String getName(){
        return Name;
    }

    public ArrayList<int> getAllLines(){
        return Lines;
    }

    public Float getX(){
        return x;
    }

    public Float getY(){
        return y;
    }
}