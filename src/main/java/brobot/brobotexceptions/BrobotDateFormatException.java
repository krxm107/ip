package brobot.brobotexceptions;

import brobot.BroBot;

/**
 * This exception is thrown when the user enters a date that is not in the correct format.
 *
 *     Examples of correct date formats:
 *         "01 Sep 2025"
 *         "01 September 2025"
 *         "1 Sep 2025"
 *         "1 September 2025"
 *
       Notes:
 *          - All month names must be in English.
 *          - Upper/lowercase does not matter ("Sep", "SEP", "september", etc.).
 *          - The leading zero for the day ("01") is optional.
 */
public final class BrobotDateFormatException extends BrobotCommandFormatException {
    private BrobotDateFormatException(final String mainMessage) {
        super(mainMessage);
    }

    /**
     * @param invalidDate
     *     The invalid date the user entered.
     *
     * @return
     *     A new instance of the BrobotDateFormatException as created by this factory constructor.
     */
    public static BrobotDateFormatException newInstance(final String invalidDate) {
        final String line1 = String.format(BroBot.ENGLISH_LANGUAGE, "The date you entered, '%s', is in the wrong format.",
                                                    invalidDate);

        final String line2 = "";

        final String line3 = "Examples of dates in the correct format are:";

        final String line4 = "    01 Sep 2025";
        final String line5 = "    1 Sep 2025";
        final String line6 = "    01 September 2025";
        final String line7 = "    1 September 2025";

        final String line8 = "";
        final String line9 = "Notes:";

        final String line10 = "    All month names must be in English.";
        final String line11 = "    Upper/lowercase does not matter (\"Sep\", \"SEP\", \"september\", etc.).";
        final String line12 = "    The leading zero for the day (\"01\") is optional.";

        final String mainMessage = String.join(System.lineSeparator(), line1, line2, line3, line4, line5, line6,
                                                line7, line8, line9, line10, line11, line12);

        return new BrobotDateFormatException(mainMessage);
    }
}
