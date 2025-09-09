package brobot.datesandtimes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import brobot.BroBot;
import brobot.brobotexceptions.BrobotDateFormatException;

/**
 * Encapsulates an immutable BrobotDate.
 */
public final class BrobotDate {

    public static final String DATE_FORMAT = "dd MMM yyyy";
    private static final DateTimeFormatter DATETIME_FORMATTER =
        DateTimeFormatter.ofPattern(BrobotDate.DATE_FORMAT, BroBot.ENGLISH_LANGUAGE);

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
     *     The user enters a date not in "dd MMM yyyy" format.
     */
    public static BrobotDate fromString(final String inputString) throws BrobotDateFormatException {
        if (inputString == null || inputString.isEmpty()) {
            throw BrobotDateFormatException.newInstance(inputString);
        }

        try {
            return new BrobotDate(LocalDate.parse(inputString, BrobotDate.DATETIME_FORMATTER));
        } catch (final DateTimeParseException wronglyFormattedInputException) {
            throw BrobotDateFormatException.newInstance(inputString);
        }
    }

    @Override
    public String toString() {
        if (logMessage == null) {
            logMessage = javaDate.format(BrobotDate.DATETIME_FORMATTER);
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
