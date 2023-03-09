package hubertmap.model.parser;

import java.io.* ;
import java.util.Scanner;

public class InterpreterCSV {
    public static void main(String[] args) {
        try {
            File file = new File("test.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                for (String s : values) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}