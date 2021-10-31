package gomedic.model.commonfield;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import gomedic.commons.util.AppUtil;

/**
 * Represents a time in the address book.
 * Guarantees: immutable; value is not null; is valid as declared in {@link #isValidTime(String)}
 */
public class Time {

    public static final String MESSAGE_CONSTRAINTS = "Time should follow one of the following format: \n"
            + "1. dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00) \n"
            + "2. dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00) \n"
            + "3. yyyy-MM-dd-HH-mm (e.g. 2022-09-15 13:00) \n";

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

    //@@author ramaven
    /**
     * Constructs a {@code Time}.
     *
     * @param time String.
     */
    public Time(String time) {
        requireNonNull(time);
        AppUtil.checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        DateTimeFormatter format;

        if (time.charAt(2) == '/') {
            format = DateTimeFormatter
                    .ofPattern("dd/MM/yyyy HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
        } else if (time.charAt(2) == '-') {
            format = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
        } else {
            format = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm")
                    .withResolverStyle(ResolverStyle.STRICT);
        }

        this.time = LocalDateTime.parse(time, format);
    }

    /**
     * Returns true if the time follows
     */
    public static boolean isValidTime(String time) {

        String[] dateTimeArr = time.split(" ");

        if (dateTimeArr.length != 2) {
            return false;
        }

        String datePart = dateTimeArr[0];
        String timePart = dateTimeArr[1];

        return isValidTimeStringPart(timePart) && isValidDateStringPart(datePart);
    }

    private static boolean isValidTimeStringPart(String timePart) {
        String[] timeArr;
        if (timePart.length() != 5) {
            return false;
        }
        if (timePart.charAt(2) == ':') {
            timeArr = timePart.split(":");
        } else {
            return false;
        }

        int hour;
        int minute;

        if (timeArr.length != 2) {
            return false;
        }

        try {
            hour = Integer.parseInt(timeArr[0]);
            minute = Integer.parseInt(timeArr[1]);
        } catch (NumberFormatException e) {
            return false;
        }

        boolean isValidHour = hour >= 0 && hour < 24;
        boolean isValidMinute = minute >= 0 && minute < 60;
        return isValidHour && isValidMinute;
    }

    private static Boolean isValidDateStringPart(String datePart) {
        String[] dateArr;
        if (datePart.length() != 10) {
            return false;
        }
        if (datePart.charAt(2) == '/') {
            dateArr = datePart.split("/");
        } else if (datePart.charAt(2) == '-' || datePart.charAt(4) == '-') {
            dateArr = datePart.split("-");
        } else {
            return false;
        }

        int day;
        int month;
        int year;

        if (dateArr.length != 3) {
            return false;
        }

        if (dateArr[0].length() == 2) {
            try {
                day = Integer.parseInt(dateArr[0]);
                month = Integer.parseInt(dateArr[1]);
                year = Integer.parseInt(dateArr[2]);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            try {
                day = Integer.parseInt(dateArr[2]);
                month = Integer.parseInt(dateArr[1]);
                year = Integer.parseInt(dateArr[0]);
            } catch (NumberFormatException e) {
                return false;
            }
        }

        boolean isValidDay = day > 0 && day <= 31;
        boolean isValidMonth = month > 0 && month <= 12;
        boolean isValidYear = year > 1900 && year < 2100;

        return isValidDay && isValidMonth && isValidYear;
    }
    //@@author

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
