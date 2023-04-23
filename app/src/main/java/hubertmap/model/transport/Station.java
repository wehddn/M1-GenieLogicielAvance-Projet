/**
 * This class represents a station in a transportation system. A station has a name, coordinates (x
 * and y), and a list of all transportation lines that serve it.
 */
package hubertmap.model.transport;

import hubertmap.model.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a station in a transportation system. A station has a name, coordinates (x
 * and y), and a list of all transportation lines that serve it.
 */
public class Station {
    private String name;
    private Float x;
    private Float y;
    private ArrayList<String> allLines;
    private Map<String, ArrayList<Time>> schedules = new HashMap<>();

    /**
     * Constructs a new station with the given name, list of lines, and coordinates. If the list of
     * lines is null, an empty list is created.
     *
     * @param name the name of the station
     * @param allLines the list of all transportation lines that serve the station
     * @param x the x-coordinate of the station's location
     * @param y the y-coordinate of the station's location
     */
    public Station(String name, ArrayList<String> allLines, Float x, Float y) {
        this.name = name;
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
     * @param name the name of the station
     * @param Line the transportation line that serves the station
     * @param x the x-coordinate of the station's location
     * @param y the y-coordinate of the station's location
     */
    public Station(String name, String Line, Float x, Float y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.allLines = new ArrayList<String>();
        this.allLines.add(Line);
        schedules.put(Line, null);
    }

    /**
     * Returns the name of the station.
     *
     * @return the name of the station
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the new name of station
     */
    public void setName(String name) {
        this.name = name;
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
     * Returns the list of all transportation lines that serve the station.
     *
     * @return the list of all transportation lines that serve the station
     */
    public Map<String, ArrayList<Time>> getSchedules() {
        return schedules;
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
        if (this.name == station.getName()
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
        return name;
    }

    /**
     * Adds a schedule for the given line at this station.
     *
     * @param line the line to add the schedule for
     * @param time the time to add to the schedule
     */
    public void addSchedule(Line line, Time time) {
        if (schedules.get(line.getName()) == null) {
            schedules.put(line.getName(), new ArrayList<>());
        }
        ArrayList<Time> times = schedules.get(line.getName());
        times.add(time);
        schedules.put(line.getName(), times);
    }

    // ! maybe not having to do this verification

    /**
     * Add a line to the station schedules. If the line already exists in the schedules, then this
     * method does nothing.
     *
     * @param lineName the name of the line to add
     */
    public void addLine(String lineName) {
        boolean exist = false;
        for (String line : schedules.keySet()) {
            if (line.contains(lineName)) {
                exist = true;
                break;
            }
        }
        if (!exist) allLines.add(lineName);
        schedules.put(lineName, null);
    }

    public ArrayList<String> getLinesNumbers() {
        Set<String> linesNumbers = new HashSet<String>();
        for (int i = 0; i < allLines.size(); i++) {
            String line = allLines.get(i);
            int index = line.indexOf(" ");
            if (index != -1) {
                line = line.substring(0, index);
                linesNumbers.add(line);
            }
        }
        return new ArrayList<String>(linesNumbers);
    }
}
