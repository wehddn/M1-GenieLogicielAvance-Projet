package hubertmap.model.parser;

import hubertmap.model.Time;

/**
 * This abstract class is a factory for parsing different types of transport data files.
 */
public abstract class ParserFactory {
    
    private String line;
    private String TerminalStationDeparture;
    private String TerminalStationArrival;
    private Time StartingTime;   
}