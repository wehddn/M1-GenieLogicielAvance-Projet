import java.io. * ;
import java.util.Scanner;

public class InterpreterCSV extends InterpreterV0 {
    public static void main(String[] args) {
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
    }
}