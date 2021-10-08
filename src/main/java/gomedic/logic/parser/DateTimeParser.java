package gomedic.logic.parser;

import gomedic.logic.parser.exceptions.ParseException;
import gomedic.model.commonfield.Time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;


/**
 * Class that handles formatting the date.
 * It handles 3 different date formats:
 * dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00)
 * dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00)
 * yyyy-MM-dd-HH-mm (e.g. 2022-09-15 03:00)
 */
public class DateTimeParser {

    static Logger logger = Logger.getLogger(DateTimeParser.class.getName());

    /**
     * Checks if input String is a valid datetime and
     * follows the time format that GoMedic allows
     */
    private static boolean isValidDateTime(String dateTime) throws ParseException {

        String[] dateTimeArr = dateTime.split(" ");

        if (dateTimeArr.length == 1) {
            logger.warning("Missing date or time");
            return false;
        }

        String date = dateTimeArr[0];
        String time = dateTimeArr[1];

        // Check if valid time
        String[] timeArr;
        if (time.length() < 5) {
            logger.warning("Invalid time");
            return false;
        }
        if (time.charAt(2) == ':') {
            timeArr = time.split(":");
        } else {
            logger.warning("Invalid time");
            return false;
        }

        int hour;
        int minute;

        if (timeArr.length < 2) {
            logger.warning("Invalid time");
            return false;
        }
        try {
            hour = Integer.parseInt(timeArr[0]);
            minute = Integer.parseInt(timeArr[1]);
        } catch (NumberFormatException e) {
            logger.warning("Invalid time");
            return false;
        }

        boolean isValidHour = hour >= 0 && hour < 24;
        boolean isValidMinute = minute >= 0 && minute < 60;
        boolean isValidTime = isValidHour && isValidMinute;

        // Check if valid date
        String[] dateArr;
        if (date.length() < 11) {
            logger.warning("Invalid date");
            return false;
        }
        if (date.charAt(2) == '/') {
           dateArr = date.split("/");
        } else if (date.charAt(2) == '-' || date.charAt(4) == '-') {
            dateArr = date.split("-");
        } else {
            logger.warning("Invalid date");
            return false;
        }

        int day;
        int month;
        int year;

        if (dateArr.length < 3) {
            logger.warning("Invalid date");
            return false;
        }
        if (dateArr[0].length() == 2) {
            try {
                day = Integer.parseInt(dateArr[0]);
                month = Integer.parseInt(dateArr[1]);
                year = Integer.parseInt(dateArr[2]);
            } catch (NumberFormatException e) {
                logger.warning("Invalid date");
                return false;
            }
        } else {
            try {
                day = Integer.parseInt(dateArr[2]);
                month = Integer.parseInt(dateArr[1]);
                year = Integer.parseInt(dateArr[0]);
            } catch (NumberFormatException e) {
                logger.warning("Invalid date");
                return false;
            }
        }

        boolean isValidDay = day > 0 && day <= 31;
        boolean isValidMonth = month > 0 && month <= 12;
        boolean isValidYear = year > 1500 && year < 10000;

        boolean isValidDate = isValidDay && isValidMonth && isValidYear;

        boolean isValidDateTime = isValidDate && isValidTime;

        return isValidDateTime;
    }

    /**
     * Formats the input datetime that was entered into a Time object
     * These input formats are accepted:
     * dd/MM/yyyy HH:mm (e.g. 15/09/2022 13:00)
     * dd-MM-yyyy HH:mm (e.g. 15-09-2022 13:00)
     * yyyy-MM-dd-HH-mm (e.g. 2022-09-15 13:00)
     *
     * @param dateLiteral date that was typed in by user
     * @return Time object
     */
    public static Time formatDate(String dateLiteral) throws ParseException {
        DateTimeFormatter format;
        if (dateLiteral.charAt(2) == '/') {
            format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        } else if (dateLiteral.charAt(2) == '-') {
            format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        } else {
            format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        }

        try {
            LocalDateTime dateFormatted = LocalDateTime.parse(dateLiteral, format);
            return new Time(dateFormatted);
        } catch (DateTimeParseException e) {
            logger.warning("Invalid datetime format");
        }
        throw new ParseException("Invalid Date format");
    }

}
