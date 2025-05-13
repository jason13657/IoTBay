package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public static String toText(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : null;
    }

    public static String toText(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMAT) : null;
    }

    public static LocalDate parseLocalDate(String text) {
        return text != null ? LocalDate.parse(text, DATE_FORMAT) : null;
    }

    public static LocalDateTime parseLocalDateTime(String text) {
        return text != null ? LocalDateTime.parse(text, DATETIME_FORMAT) : null;
    }
}