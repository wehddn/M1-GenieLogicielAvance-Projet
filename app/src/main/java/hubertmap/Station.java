public class Station {
    private String Name;
    private ArrayList<Line> Lines;
    private Float x;
    private Float y;

    public Station(String Name, ArrayList<Line> Lines, Float x, Float y){
        this.Name = Name;
        this.x = x;
        this.y = y;
        if(Lines != null){
            this.Lines = Lines;
        }
        else{
            this.Lines = new ArrayList<Line>();
        }
    }

    public String getName(){
        return Name;
    }

    public ArrayList<Line> getLines(){
        return Lines;
    }

    public Float getX(){
        return x;
    }

    public Float getY(){
        return y;
    }
}