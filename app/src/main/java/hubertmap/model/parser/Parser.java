package hubertmap.model.parser;

import hubertmap.model.Time;
import hubertmap.model.transport.Station;
import java.io.*;
import java.util.Scanner;

public class Parser extends ParserFactory {
    // StartingStation; StartingStationLatitude; StartingStationLongitude; EndingStation;
    // EndingStationLatitude; EndingStationLongitude; Line; Time; Distance;
    /** Parses the input CSV file and returns the network data as a tuple of Stations and Edges. */
    public Parser() {
        try {
            File file = new File("src/main/java/hubertmap/model/map_data.csv");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");

                String station1Name = values[0].trim();
                float station1Lat = Float.parseFloat(values[1].trim().split(",")[0]);
                float station1Lon = Float.parseFloat(values[1].trim().split(",")[1]);
                String station2Name = values[2].trim();
                float station2Lat = Float.parseFloat(values[3].trim().split(",")[0]);
                float station2Lon = Float.parseFloat(values[3].trim().split(",")[1]);
                String lineName = values[4].trim();
                String timeString = values[5].trim();
                Time time =
                        new Time(
                                Integer.parseInt(timeString.split(":")[0]),
                                Integer.parseInt(timeString.split(":")[1]));
                float distance = Float.parseFloat(values[6].trim());
                Station station1 = new Station(station1Name, lineName, station1Lat, station1Lon);
                Station station2 = new Station(station2Name, lineName, station2Lat, station2Lon);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'a pas été trouvé : " + e.getMessage());
        }
    }
}
