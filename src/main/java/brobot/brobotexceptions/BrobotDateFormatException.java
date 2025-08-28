package brobot.brobotexceptions;

import brobot.datesandtimes.BrobotDate;

/**
 * This exception is thrown when the user enters a date that is not in the 'dd MMM yyyy' format.
 */
public final class BrobotDateFormatException extends BrobotCommandFormatException {
    private BrobotDateFormatException(final String mainMessage) {
        super(mainMessage);
    }

    /**
     * @param invalidDate
     * The invalid date the user entered.
     *
     * @return
     * A new instance of the BrobotDateFormatException as created by this factory constructor.
     */
    public static BrobotDateFormatException newInstance(final String invalidDate) {
        final String line1 = String.format("The date you entered, '%s', is in the wrong format.", invalidDate);

        final String line2 = String.format("All dates must be in the '%s' format, explained as follows:",
                                            BrobotDate.DATE_FORMAT);

        final String line3 = "dd: Day must be a day in the month, with exactly 2 digits, starting with 0 if necessary.";

        final String line4 = "MMM: Month must be the first three letters of the month, " +
                                "with the first in uppercase and the last 2 in lowercase.";


        final String line5 = "yyyy: Year must have exactly 4 digits.";

        return new BrobotDateFormatException(String.join("\n", "", line1, line2, line3, line4, line5, ""));
    }
}
