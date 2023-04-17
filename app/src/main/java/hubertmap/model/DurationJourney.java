package hubertmap.model;

/*  Time given in station csv file is in a specific format : NN:NN
 ** For example : 5:53 means 55 sec and 3 tenths of a second
 ** 12:45 => 1 minute, 24 second and 5 tenths of a second
 ** In this file we're not going to worry about tenths of a second
 ** because user doesn't need to have such precision
 */
// TODO can be improved with better understanding of the time format
/** The DurationJourney class represents a duration in minutes and secondes */
public class DurationJourney {

    /** The minute component of the duration */
    private int minute;

    /** The seconde component of the duration */
    private int seconde;

    /**
     * Constructs a DurationJourney object with the specified minute and seconde.
     *
     * @param leftTimeOperator the minute component
     * @param rightTimeOperator the seconde component
     */
    public DurationJourney(String leftTimeOperator, String rightTimeOperator) {
        if (leftTimeOperator.length() == 2) {
            minute = Character.getNumericValue(leftTimeOperator.charAt(0));
            seconde =
                    Integer.parseInt(
                            String.valueOf(leftTimeOperator.charAt(1))
                                    .concat(String.valueOf(rightTimeOperator.charAt(0))));
        } else {
            minute = 0;
            seconde =
                    Integer.parseInt(
                            String.valueOf(leftTimeOperator.charAt(0))
                                    .concat(String.valueOf(rightTimeOperator.charAt(0))));
        }
    }

    /**
     * Returns the seconde property
     *
     * @return the seconde propery
     */
    public int getSeconde() {
        return seconde;
    }

    /**
     * Sets the seconde property
     *
     * @param seconde the seconde property
     */
    public void setSeconde(int seconde) {
        this.seconde = seconde;
    }

    /**
     * Returns the minute property
     *
     * @return the minute property
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Sets the minute property
     *
     * @param minute the minute property
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * Returns a string representation of the time in the format "MM:SS".
     *
     * @return a string representation of the time
     */
    @Override
    public String toString() {
        return String.format("(minutes : %d, secondes : %d)", minute, seconde);
    }
}
