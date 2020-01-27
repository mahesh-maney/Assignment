package com.swiss.re.exchange.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This is a date utility class for converting date in String format to LocalDatetime.
 * @author ManeyMR
 * @version 1.0
 * @since   2020-01-25
 */
public class DateUtil {
    public static LocalDateTime converter(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss MM/dd/yyyy");
        return LocalDateTime.parse(date, formatter);
    }
}
