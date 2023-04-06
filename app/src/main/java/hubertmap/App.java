package hubertmap;

import hubertmap.controller.Controller;

/**
 * The App class contains a method to retrieve a greeting message. The main method of this class
 * prints the greeting message to the console.
 */
public class App {
    /**
     * Retrieves a greeting message.
     *
     * @return a String containing the greeting message
     */
    public String getGreeting() {
        return "Hello World!";
    }

    /**
     * The main method of this class and application. Prints the greeting message returned by the
     * getGreeting() method to the console.
     *
     * @param args an array of command-line arguments (unused in this implementation)
     */
    public static void main(String[] args) {
        System.out.println(new App().getGreeting());

        new Controller();
    }
}
