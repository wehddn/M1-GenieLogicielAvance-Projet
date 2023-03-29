package hubertmap.model.parser;

import java.io.*;
import java.util.Scanner;

public class Parser {

    // should be called by ParserFactory only
    protected Parser() {

    }

    // we should return an Object of type Network instead
    public String parse(String fileName) {
        Scanner scanner = null;
        String returnValue = "";
        try {
            File file = new File(fileName);
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                for (String s : values) {
                    returnValue += (s + " ");
                }
                returnValue += '\n';
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null; // we should probably throw the exceptions instead
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return returnValue;
    }
}