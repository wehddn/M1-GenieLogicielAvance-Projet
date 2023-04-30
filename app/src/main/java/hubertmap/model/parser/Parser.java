package hubertmap.model.parser;

import hubertmap.model.DurationJourney;
import hubertmap.model.Time;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Line;
import hubertmap.model.transport.Network;
import hubertmap.model.transport.Station;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/** The Parser class parses the input CSV files */
public class Parser extends ParserFactory {

    Network network;
    /** The list of all stations in the database. */
    public List<Station> stations = new ArrayList<>();
    /** The list of all edges in the database. */
    public List<EdgeTransport> edges = new ArrayList<>();
    /** The list of all lines in the database with their starting times. */
    public Map<Line, ArrayList<DurationJourney>> dataLine = new HashMap<>();

    /**
     * The constructor of the Parser class. It calls the parseStations() and parseLines() methods to
     * parse stations and lines from the CSV files. If the file is not found, it catches the
     * FileNotFoundException and prints an error message. If any other exception occurs, it catches
     * the Exception and prints an error message.
     */
    public Parser() {
        try {
            parseStations(openFile("ressource/map_data.csv"));
            parseLines(openFile("ressource/timetables.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'a pas été trouvé : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
    /**
     * Returns the network edges.
     *
     * @return the network edges.
     */
    public Network getEdges() {
        return network;
    }

    /**
     * Opens the file at the given path and returns a File object. If the file is not found, it
     * catches the Exception and prints an error message.
     *
     * @param path the path of the file to open.
     * @return a File object of the file at the given path.
     */
    public File openFile(String path) {
        try {
            File file = new File(path);
            return file;
        } catch (Exception e) {
            System.out.println("Le fichier n'a pas été trouvé : " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks if a station with the given name and line already exists in the database. If it does,
     * it returns the station object. If it doesn't, it returns null.
     *
     * @param name the name of the station to check.
     * @param line the line of the station to check.
     * @return the station object if it already exists, or null if it doesn't.
     */
    public Station stationAlreadyExist(String name, String line) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(name)) {
                return stations.get(i);
            }
        }
        return null;
    }

    /**
     * Checks if a line with the given name already exists in the database. If it does, it returns
     * the line object. If it doesn't, it throws an Exception with an error message.
     *
     * @param name the name of the line to check.
     * @return the line object if it already exists.
     * @throws Exception if the line doesn't already exist in the database.
     */
    public Line lineAlreadyExist(String name) throws Exception {

        for (Map.Entry<Line, ArrayList<DurationJourney>> entry : dataLine.entrySet()) {
            if (entry.getKey().getName().equals(name)) {
                return entry.getKey();
            }
        }
        throw new Exception("Line doesn't already exist in database");
    }

    // StartingStation; StartingStationLatitude; StartingStationLongitude; EndingStation;
    // EndingStationLatitude; EndingStationLongitude; Line; Time; Distance;
    /**
     * Parses a file containing information about stations, lines and their connections, and creates
     * a network graph representation of this data. This method reads a CSV file, with each line
     * containing information about a connection between two stations.
     *
     * @param file the CSV file containing the stations and connections information
     * @throws Exception if there is an error reading or parsing the file
     */
    public void parseStations(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        network = new Network();
        ArrayList<DurationJourney> durationJourneys = new ArrayList<>();
        String lastLineName = null;
        Station lastStation = null;
        Line currentLine = null;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(";");

            String station1Name = values[0].trim();
            float station1Lat = Float.parseFloat(values[1].trim().split(",")[0]);
            float station1Lon = Float.parseFloat(values[1].trim().split(",")[1]);
            String station2Name = values[2].trim();
            float station2Lat = Float.parseFloat(values[3].trim().split(",")[0]);
            float station2Lon = Float.parseFloat(values[3].trim().split(",")[1]);
            String lineName = values[4].trim();
            String timeString = values[5].trim();
            DurationJourney time =
                    new DurationJourney(timeString.split(":")[0], timeString.split(":")[1]);
            durationJourneys.add(time);

            float distance = Float.parseFloat(values[6].trim());
            Station station1;
            Station station2;

            station1 = stationAlreadyExist(station1Name, lineName);
            if (station1 == null) {
                station1 = new Station(station1Name, lineName, station1Lat, station1Lon);
                stations.add(station1);
            } else station1.addLine(lineName);

            station2 = stationAlreadyExist(station2Name, lineName);
            if (station2 == null) {
                station2 = new Station(station2Name, lineName, station2Lat, station2Lon);
                stations.add(station2);
            } else station2.addLine(lineName);

            if (lastStation == null) {
                lastStation = station2;
            }
            if (lastLineName == null) {
                lastLineName = lineName;
                currentLine = new Line(lineName, station1);
                dataLine.put(currentLine, durationJourneys);
            }

            if (!lastLineName.equals(lineName)) {
                currentLine.setTerminalStationArrival(lastStation);
                currentLine = new Line(lineName, station1);
                durationJourneys.remove(durationJourneys.size() - 1);

                durationJourneys = new ArrayList<>();
                dataLine.put(currentLine, durationJourneys);
                durationJourneys.add(time);
                lastLineName = lineName;
            }

            if (station1 != station2) {
                currentLine.addStationsIfNotAlreadyExist(station1);
                currentLine.addStationsIfNotAlreadyExist(station2);

                EdgeTransport edge =
                        new EdgeTransport(station1, station2, time, distance, lineName);
                network.addEdge(edge, station1, station2);
                edges.add(edge);
                lastStation = station2;
            }
        }
        reader.close();
        currentLine.setTerminalStationArrival(lastStation);
        network.setDataLine(dataLine);
    }

    /**
     * Parses a file containing information about the lines and their schedules, and fills in the
     * schedule information for each station on each line. This method reads a CSV file, with each
     * line containing information about a line's schedule.
     *
     * @param file the CSV file containing the lines and schedules information
     * @throws Exception if there is an error reading or parsing the file, or if the data given
     *     doesn't match
     */
    public void parseLines(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        String csvLine;

        while ((csvLine = reader.readLine()) != null) {
            String[] values = csvLine.split(";");
            String terminus = values[1];

            String completeNameLine = values[0] + " variant " + values[3];
            Time start =
                    new Time(
                            Integer.parseInt(values[2].split(":")[0]),
                            Integer.parseInt(values[2].split(":")[1]),
                            0);

            Line currentLine = lineAlreadyExist(completeNameLine);
            if (!terminus.equals(currentLine.getTerminalStationDeparture().getName())) {
                throw new Exception(
                        "Data given doesn't match\nThis line had "
                                + currentLine.getTerminalStationDeparture().getName()
                                + " as terminus start. The file has given "
                                + terminus
                                + " as terminus start station");
            } else {
                currentLine.addStart(start);
            }
        }
        reader.close();
        this.fillStationsSchedulesFromTerminusLineStart();
    }

    /**
     * Fills in the schedule information for each station on each line, based on the start times and
     * duration journeys between stations specified in the dataLine map.
     */
    public void fillStationsSchedulesFromTerminusLineStart() {
        Time timeToFillStationsSchedules = null;
        int i = 0;
        for (Line line : dataLine.keySet()) {
            for (Time time : line.starts) {
                timeToFillStationsSchedules = time;
                i = 0;
                for (DurationJourney dj : dataLine.get(line)) {
                    line.allStations
                            .get(i)
                            .addSchedule(line, new Time(timeToFillStationsSchedules));
                    timeToFillStationsSchedules =
                            timeToFillStationsSchedules.increaseWithADurationJourney(dj);
                    i++;
                }
                line.allStations.get(i).addSchedule(line, new Time(timeToFillStationsSchedules));
            }
        }
    }
}
