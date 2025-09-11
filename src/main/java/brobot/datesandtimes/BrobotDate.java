package brobot.datesandtimes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;

import brobot.brobotexceptions.BrobotDateFormatException;

/**
 * Encapsulates an immutable BrobotDate.
 */
public final class BrobotDate {
    
    // Date formatter for months like 'Sep'.
    // I used ChatGPT AI to help me write this code to improve date input flexibility. This deals with 3-letter months.
    private static final DateTimeFormatter SHORT_MONTH_DATE_TIME_INPUT_FORMATTER = (new DateTimeFormatterBuilder())
                                                                                    .parseCaseInsensitive()
                                                                                    .appendPattern("d MMM uuuu")
                                                                                    .toFormatter(Locale.ENGLISH)
                                                                                    .withResolverStyle(ResolverStyle.STRICT);

    // Date formatter for months like 'September'.
    // I used ChatGPT AI to help me write this code to improve date input flexibility. This deals with full-name months.
    private static final DateTimeFormatter FULL_MONTH_DATE_TIME_INPUT_FORMATTER = (new DateTimeFormatterBuilder())
                                                                                    .parseCaseInsensitive()
                                                                                    .appendPattern("d MMMM uuuu")
                                                                                    .toFormatter(Locale.ENGLISH)
                                                                                    .withResolverStyle(ResolverStyle.STRICT);

    private static final DateTimeFormatter DATE_TIME_OUTPUT_FORMATTER =
            DateTimeFormatter.ofPattern("d MMM uuuu", Locale.ENGLISH);

    // This field should never be null.
    private final LocalDate javaDate;

    private Integer hashCodeCache = null;

    private String logMessage = null;

    private BrobotDate(final LocalDate javaDate) {
        this.javaDate = javaDate;
    }

    /**
     * Factory constructor for BrobotDate
     *
     * @throws BrobotDateFormatException
     *     The user enters a date not in the correct format.
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
    public static BrobotDate fromString(final String inputString) throws BrobotDateFormatException {
        if (inputString == null || inputString.isEmpty()) {
            throw BrobotDateFormatException.newInstance(inputString);
        }

        try {
            return new BrobotDate(LocalDate.parse(inputString, BrobotDate.SHORT_MONTH_DATE_TIME_INPUT_FORMATTER));
        } catch (final DateTimeParseException wronglyFormattedInputException) {
            try {
                return new BrobotDate(LocalDate.parse(inputString, BrobotDate.FULL_MONTH_DATE_TIME_INPUT_FORMATTER));
            } catch (final DateTimeParseException wronglyFormattedInputException2) {
                throw BrobotDateFormatException.newInstance(inputString);
            }
        }
    }

    @Override
    public String toString() {
        if (logMessage == null) {
            logMessage = javaDate.format(BrobotDate.DATE_TIME_OUTPUT_FORMATTER);
        }

        return logMessage;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof BrobotDate)) {
            return false;
        }

        final BrobotDate castedOther = (BrobotDate) (other);
        return javaDate.equals(castedOther.javaDate);
    }

    @Override
    public int hashCode() {
        if (hashCodeCache == null) {
            hashCodeCache = javaDate.hashCode();
        }

        return hashCodeCache;
    }
}
