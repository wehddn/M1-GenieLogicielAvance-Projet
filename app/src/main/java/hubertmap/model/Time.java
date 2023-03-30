package hubertmap.model;

/**
 * The Time class represents a time of day in hours and minutes.
 */
public class Time{
    /**
 * The hour component of the time.
 */
private int hour;

/**
 * The minute component of the time.
 */
private int minute;

/**
 * Constructs a Time object with the specified hour and minute.
 *
 * @param hour the hour component of the time
 * @param minute the minute component of the time
 */
public Time(int hour, int minute) {
    this.hour = hour;
    this.minute = minute;
}

/**
 * Returns the hour component of the time.
 *
 * @return the hour component of the time
 */
public int getHour() {
    return hour;
}

/**
 * Sets the hour component of the time.
 *
 * @param hour the hour component of the time
 */
public void setHour(int hour) {
    this.hour = hour;
}

/**
 * Returns the minute component of the time.
 *
 * @return the minute component of the time
 */
public int getMinute() {
    return minute;
}

/**
 * Sets the minute component of the time.
 *
 * @param minute the minute component of the time
 */
public void setMinute(int minute) {
    this.minute = minute;
}

/**
 * Returns a string representation of the time in the format "HH:MM".
 *
 * @return a string representation of the time
 */
@Override
public String toString() {
   return String.format("%02d:%02d", hour, minute);
}
}


