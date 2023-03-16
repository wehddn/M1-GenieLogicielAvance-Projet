package hubertmap.model;

import java.util.ArrayList;

public class Station {
    private String Name;
    private Float x;
    private Float y;
    private ArrayList<String> allLines;

    public Station(String Name, ArrayList<String> allLines, Float x, Float y){
        this.Name = Name;
        this.x = x;
        this.y = y;
        if(allLines != null){
            this.allLines = allLines;
        }
        else{
            this.allLines = new ArrayList<String>();
        }
    }

    public String getName(){
        return Name;
    }

    public ArrayList<String> getAllLines(){
        return allLines;
    }

    public Float getX(){
        return x;
    }

    public Float getY(){
        return y;
    }

    public boolean equals(Station station){
        if(this.Name == station.getName() && this.x == station.getX() && this.y == station.getY()){
            return true;
        }
        else{
            return false;
        }
    }
}