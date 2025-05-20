package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParser {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;
    // DB에 저장된 포맷: "yyyy-MM-dd HH:mm:ss"
    private static final DateTimeFormatter DB_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String toText(LocalDate date) {
        return date != null ? date.format(DATE_FORMAT) : null;
    }

    public static String toText(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DB_DATETIME_FORMAT) : null;
    }

    public static LocalDate parseLocalDate(String text) {
        return text != null ? LocalDate.parse(text, DATE_FORMAT) : null;
    }

    public static LocalDateTime parseLocalDateTime(String text) {
        return text != null ? LocalDateTime.parse(text, DB_DATETIME_FORMAT) : null;
    }
}
