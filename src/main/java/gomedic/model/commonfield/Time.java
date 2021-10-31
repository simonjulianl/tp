package gomedic.model.commonfield;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.logging.Logger;

import gomedic.commons.core.LogsCenter;
import gomedic.commons.util.AppUtil;

/**
 * Represents a time in the address book.
 * Guarantees: immutable; value is not null; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Please check again that your time is valid ! \n"
            + "Some common errors include invalid dates (e.g. 29th Feb on non-leap years) and months (13th month), "
            + "invalid time such as 24:00 instead of 00:00 \n"
            + "Time should also follow one of the following format: \n"
            + "1. dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00) \n"
            + "2. dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00) \n"
            + "3. yyyy-MM-dd-HH-mm (e.g. 2022-09-15 13:00) \n";
    public static final String DEFAULT_SECOND = ":00";
    private static final Logger logger = LogsCenter.getLogger(Time.class);
    public final LocalDateTime time;

    /**
     * Constructs a {@code Time}.
     *
     * @param time LocalDateTime object.
     */
    public Time(LocalDateTime time) {
        requireNonNull(time);
        this.time = time;
    }

    /**
     * Constructs a {@code Time}.
     *
     * @param time String.
     */
    public Time(String time) {
        requireNonNull(time);
        AppUtil.checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        DateTimeFormatter format = getDateTimeFormatter(time);
        this.time = LocalDateTime.parse(time + DEFAULT_SECOND, format);
    }

    /**
     * Returns true if the time follows
     */
    public static boolean isValidTime(String time) {
        time = time + DEFAULT_SECOND;

        DateTimeFormatter format = getDateTimeFormatter(time);
        try { // not good practice but no function to check is valid or not except by parsing
            LocalDateTime.parse(time, format);
            return true;
        } catch (DateTimeParseException e) {
            logger.warning(e.getMessage());
            return false;
        }
    }

    private static DateTimeFormatter getDateTimeFormatter(String time) {
        DateTimeFormatter format;

        if (time.charAt(2) == '/') {
            format = DateTimeFormatter
                    .ofPattern("dd/MM/uuuu HH:mm:ss");
        } else if (time.charAt(2) == '-') {
            format = DateTimeFormatter
                    .ofPattern("dd-MM-uuuu HH:mm:ss");
        } else {
            format = DateTimeFormatter
                    .ofPattern("uuuu-MM-dd HH:mm:ss");
        }

        format = format.withResolverStyle(ResolverStyle.STRICT);

        return format;
    }

    /**
     * @return String representation of full date time.
     */
    @Override
    public String toString() {
        return toDateString() + " " + toTimeString();
    }

    /**
     * @return String representation of just the date field.
     */
    public String toDateString() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy").format(time.toLocalDate());
    }

    /**
     * @return String representation of just the time field.
     */
    public String toTimeString() {
        return DateTimeFormatter.ofPattern("HH:mm").format(time.toLocalTime());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && time.isEqual(((Time) other).time)); // state check
    }

    /**
     * @return hash over the entire date and time field.
     */
    @Override
    public int hashCode() {
        return time.hashCode();
    }

    /**
     * @return true if the date of other time is the same as this.
     */
    public boolean equalsDateTime(Time other) {
        if (other == this) {
            return true;
        }

        if (other == null) {
            return false;
        }

        return other.time.toLocalDate().isEqual(time.toLocalDate());
    }

    /**
     * @param other Time.
     * @return true is this Time is after others.
     */
    public boolean isAfter(Time other) {
        return time.isAfter(other.time);
    }

    /**
     * @param other Time.
     * @return true is this Time is before others.
     */
    public boolean isBefore(Time other) {
        return time.isBefore(other.time);
    }
}
