/**
 * This class represents a station in a transportation system. A station has a name, coordinates (x
 * and y), and a list of all transportation lines that serve it.
 */
package hubertmap.model.transport;

import java.util.ArrayList;

public class Station {
    private String Name;
    private Float x;
    private Float y;
    private ArrayList<String> allLines;

    /**
     * Constructs a new station with the given name, list of lines, and coordinates. If the list of
     * lines is null, an empty list is created.
     *
     * @param Name the name of the station
     * @param allLines the list of all transportation lines that serve the station
     * @param x the x-coordinate of the station's location
     * @param y the y-coordinate of the station's location
     */
    public Station(String Name, ArrayList<String> allLines, Float x, Float y) {
        this.Name = Name;
        this.x = x;
        this.y = y;
        if (allLines != null) {
            this.allLines = allLines;
        } else {
            this.allLines = new ArrayList<String>();
        }
    }
    /**
     * Constructs a new station with the given name, line, and coordinates. The station is served by
     * only one transportation line.
     *
     * @param Name the name of the station
     * @param Line the transportation line that serves the station
     * @param x the x-coordinate of the station's location
     * @param y the y-coordinate of the station's location
     */
    public Station(String Name, String Line, Float x, Float y) {
        this.Name = Name;
        this.x = x;
        this.y = y;
        this.allLines = new ArrayList<String>();
        this.allLines.add(Line);
    }

    /**
     * Returns the name of the station.
     *
     * @return the name of the station
     */
    public String getName() {
        return Name;
    }
    /**
     * Returns the list of all transportation lines that serve the station.
     *
     * @return the list of all transportation lines that serve the station
     */
    public ArrayList<String> getAllLines() {
        return allLines;
    }

    /**
     * Returns the x-coordinate of the station's location.
     *
     * @return the x-coordinate of the station's location
     */
    public Float getX() {
        return x;
    }
    /**
     * Returns the y-coordinate of the station's location.
     *
     * @return the y-coordinate of the station's location
     */
    public Float getY() {
        return y;
    }
    /**
     * Determines whether this station is equal to the specified station. Two stations are
     * considered equal if they have the same name and location.
     *
     * @param station the station to compare this station to
     * @return true if this station is equal to the specified station, false otherwise
     */
    public boolean equals(Station station) {
        if (this.Name == station.getName()
                && this.x == station.getX()
                && this.y == station.getY()) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * Returns the name of the station.
     *
     * @return the name of the station
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Name;
    }
}
