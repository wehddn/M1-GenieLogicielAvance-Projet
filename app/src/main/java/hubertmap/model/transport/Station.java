package hubertmap.model.transport;

import java.util.ArrayList;
import java.util.List;

public class Station {
    private String name;
    private ArrayList<Line> lines;
    private Float x;
    private Float y;

    public Station(String name, ArrayList<Line> lines, Float x, Float y){
        this.name = name;
        this.x = x;
        this.y = y;
        this.lines = lines;
    }

    public String getName(){
        return name;
    }

    public List<Line> getLines(){
        return lines;
    }

    public Float getX(){
        return x;
    }

    public Float getY(){
        return y;
    }
}