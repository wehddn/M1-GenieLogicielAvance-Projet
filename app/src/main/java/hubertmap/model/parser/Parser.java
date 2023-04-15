package hubertmap.model.parser;

import hubertmap.model.Time;
import hubertmap.model.transport.EdgeTransport;
import hubertmap.model.transport.Network;
import hubertmap.model.transport.Station;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;

public class Parser extends ParserFactory {
    // StartingStation; StartingStationLatitude; StartingStationLongitude; EndingStation;
    // EndingStationLatitude; EndingStationLongitude; Line; Time; Distance;

    Network network;
    /** Parses the input CSV file and returns the network data as a tuple of Stations and Edges. */
    private List<Station> list = new ArrayList<>();

    public Parser() {
        try {
            parseCsv(openFile("src/main/java/hubertmap/model/map_data.csv"));
        } catch (Exception e) {
            System.out.println("Le fichier n'a pas été trouvé : " + e.getMessage());
        }
    }

    public Network getEdges() {
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
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i);
            }
        }
        return null;
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

    public void parseCsv(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        String line;
        network = new Network();
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(";");

            String station1Name = stripAccents(values[0].trim());
            float station1Lat = Float.parseFloat(values[1].trim().split(",")[0]);
            float station1Lon = Float.parseFloat(values[1].trim().split(",")[1]);
            String station2Name = stripAccents(values[2].trim());
            float station2Lat = Float.parseFloat(values[3].trim().split(",")[0]);
            float station2Lon = Float.parseFloat(values[3].trim().split(",")[1]);
            String lineName = values[4].trim();
            String timeString = values[5].trim();
            Time time =
                    new Time(
                            Integer.parseInt(timeString.split(":")[0]),
                            Integer.parseInt(timeString.split(":")[1]));
            float distance = Float.parseFloat(values[6].trim());
            Station station1;
            Station station2;

            station1 = stationAlreadyExist(station1Name, lineName);
            if (station1 == null) {
                station1 = new Station(station1Name, lineName, station1Lat, station1Lon);
                list.add(station1);
            } else station1.addLine(lineName);

            station2 = stationAlreadyExist(station2Name, lineName);
            if (station2 == null) {
                station2 = new Station(station2Name, lineName, station2Lat, station2Lon);
                list.add(station2);
            } else station2.addLine(lineName);

            EdgeTransport edge = new EdgeTransport(station1, station2, time, distance);
            network.addEdge(edge, station1, station2);
        }
        reader.close();
    }
}
