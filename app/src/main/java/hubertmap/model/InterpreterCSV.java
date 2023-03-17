package hubertmap.model;
import java.io.*;
import java.util.Scanner;

public class InterpreterCSV extends FabricInterpreter{
    /*public static void main(String[] args) {
        try {
            File file = new File("test.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                Edge edge = new Edge(values[0], values[1], values[2]);
                System.out.println(edge);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }*/
    //Jeanne d’arc; 43.60887,1.44544; Jean Jaurès; 43.60573,1.44883; B; 1:42; 8.43;
    //Create a parser that uses a CSV file with this format to create an edge, using scanner and split = ","
    //StartingStation; StartingStationLatitude; StartingStationLongitude; EndingStation; EndingStationLatitude; EndingStationLongitude; Line; Time; Distance;
    public static void main(String[] args) {
        try {
            File file = new File("./map_data.csv");
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
                Time time = new Time(Integer.parseInt(timeString.split(":")[0]), Integer.parseInt(timeString.split(":")[1]));
                float distance = Float.parseFloat(values[6].trim());
                Station station1 = new Station(station1Name,lineName, station1Lat, station1Lon);
                Station station2 = new Station(station2Name,lineName, station2Lat, station2Lon);
                Edge edge = new Edge(station1, station2, time, distance);

                 System.out.println("Created edge: " + edge);
                
            }

       

     scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'a pas été trouvé : " + e.getMessage());        }
    }

}