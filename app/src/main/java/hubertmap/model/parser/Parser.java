package hubertmap.model.parser;

import hubertmap.model.DurationJourney;
import hubertmap.model.Time;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Line;
import hubertmap.model.transport.Network;
import hubertmap.model.transport.Station;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;

public class Parser extends ParserFactory {

    Network network;
    /** Parses the input CSV file and returns the network data as a tuple of Stations and Edges. */
    public List<Station> stations = new ArrayList<>();

    public List<EdgeTransport> edges = new ArrayList<>();
    public Map<Line, ArrayList<DurationJourney>> dataLine = new HashMap<>();

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

    public Network getNetwork() {
        return network;
    }

    public File openFile(String path) {
        try {
            File file = new File(path);
            return file;
        } catch (Exception e) {
            System.out.println("Le fichier n'a pas été trouvé : " + e.getMessage());
            return null;
        }
    }

    public Station stationAlreadyExist(String name, String line) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(name)) {
                return stations.get(i);
            }
        }
        return null;
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

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
            station1Name = stripAccents(station1Name);
            float station1Lat = Float.parseFloat(values[1].trim().split(",")[0]);
            float station1Lon = Float.parseFloat(values[1].trim().split(",")[1]);
            String station2Name = values[2].trim();
            station2Name = stripAccents(station2Name);
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
            currentLine.addStationsIfNotAlreadyExist(station1);
            currentLine.addStationsIfNotAlreadyExist(station2);

            EdgeTransport edge = new EdgeTransport(station1, station2, time, distance);
            network.addEdge(edge, station1, station2);
            edges.add(edge);

            lastStation = station2;
        }
        reader.close();
        currentLine.setTerminalStationArrival(lastStation);
    }

    /*
     * Parse a file that has the following structure :
     * Ligne;Terminus;Heure:Minutes;Variante
     */
    public void parseLines(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        String csvLine;

        while ((csvLine = reader.readLine()) != null) {
            String[] values = csvLine.split(";");
            String terminus = values[1];
            terminus = stripAccents(terminus);

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
