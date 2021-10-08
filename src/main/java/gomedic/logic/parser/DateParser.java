package gomedic.logic.parser;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Class that handles formatting the date.
 * It handles 3 different date formats:
 * dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00)
 * dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00)
 * yyyy-MM-dd-HH-mm (e.g. 2022-09-15-13-00)
 */
public class DateParser {

    /**
     * Checks if input String is a valid time and
     * follows one of the 2 time formats that GoMedic allows:
     * 13:00
     * 13-00
     */
    public static boolean isValidTime(String time){

        String[] timeArr;

        if (time.charAt(2) == ':') {
            timeArr = time.split(":");
        } else if (time.charAt(2) == '-') {
            timeArr = time.split("-");
        } else {
            return false;
        }

        int hour;
        int minute;

        try {
             hour = Integer.parseInt(timeArr[0]);
             minute = Integer.parseInt(timeArr[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid time");
            return false;
        }

        boolean isValidHour = hour >= 0 && hour < 24;
        boolean isValidMinute = minute >= 0 && minute < 60;

        return (isValidHour && isValidMinute);
    }

    /**
     * Formats the input datetime that was entered into a Date object
     * These input formats are accepted:
     * dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00)
     * dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00)
     * yyyy-MM-dd-HH-mm (e.g. 2022-09-15-13-00)
     *
     * @param dateLiteral date that was typed in by user
     * @return Date object
     */
    public static LocalDateTime formatDate(String dateLiteral) {
        DateTimeFormatter format;
        if (dateLiteral.charAt(2) == '/'){
            format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        } else if (dateLiteral.charAt(2) == '-') {
            format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        } else {
            format = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        }

        try {
            LocalDateTime dateFormatted = LocalDateTime.parse(dateLiteral, format);
            return dateFormatted;
        } catch (DateTimeParseException e) {
            System.out.println("Incorrect date format");
        }
        return LocalDateTime.now();
    }

    /**
     * Parses a LocalDateTime object and converts it into the following
     * reader-friendly format:
     * EEE, dd MMM yyyy, HHmm
     * eg. Thu, 15 Sep 2022, 1300
     * @param dateTime LocalDateTime object
     * @return String dateStr
     */
    public static String parseDateToString(LocalDateTime dateTime) {
        // Format of LocalDateTime object:
        // YYYY-MM-DDTHH:MM
        // Example: 2022-09-15T13:00

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy, HHmm");
        String dateTimeStr = dateTime.format(formatter);

        return dateTimeStr;
    }

}
