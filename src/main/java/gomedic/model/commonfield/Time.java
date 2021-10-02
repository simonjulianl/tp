package gomedic.model.commonfield;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import gomedic.commons.util.CollectionUtil;

/**
 * Represents a time in the address book.
 * Guarantees: immutable; value is not null.
 */
public class Time {

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
     * @param day Date from 1-31 depending on the month.
     * @param month Month 1-12.
     * @param year yyyy format.
     * @param hour 0-24.
     * @param minute 0-60.
     */
    public Time(int day, int month, int year, int hour, int minute) {
        CollectionUtil.requireAllNonNull(day, month, year, hour, minute);
        time = LocalDateTime.of(year, month, day, hour, minute);
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
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).format(time.toLocalDate());
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
    public boolean equalsDate(Time other) {
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
