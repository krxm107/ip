package brobot.datesandtimes;

import brobot.brobotexceptions.BrobotDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class BrobotDate {

    // This parameter should never be null.
    private final LocalDate javaDate;

    public static final String DATE_FORMAT = "dd MMM yyyy";
    private static final DateTimeFormatter DATETIME_FORMATTER
            = DateTimeFormatter.ofPattern(BrobotDate.DATE_FORMAT);

    private BrobotDate (final LocalDate javaDate) {
        this.javaDate = javaDate;
    }

    public static BrobotDate fromString (final String inputString) throws BrobotDateFormatException {
        if (inputString == null || inputString.isEmpty()) {
            throw BrobotDateFormatException.newInstance(inputString);
        }

        try {
            return new BrobotDate(LocalDate.parse(inputString, BrobotDate.DATETIME_FORMATTER));
        }  catch (final DateTimeParseException wronglyFormattedInputException) {
            throw BrobotDateFormatException.newInstance(inputString);
        }
    }

    @Override
    public String toString() {
        return this.javaDate.format(BrobotDate.DATETIME_FORMATTER);
    }

    @Override
    public boolean equals (final Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof BrobotDate)) {
            return false;
        }

        final BrobotDate castedOther = (BrobotDate) (other);
        return this.javaDate.equals(castedOther.javaDate);
    }

    private Integer hashCodeCache = null;
    @Override
    public int hashCode() {
        if (this.hashCodeCache == null) {
            this.hashCodeCache = this.javaDate.hashCode();
        }

        return this.hashCodeCache;
    }
}
