package io.blacktoast.utils.sqlexecutor.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TypesConversor {

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param value
     * @return
     */
    public static String toString(Object value) {

        if (value == null) {
            return null;
        }

        if (value instanceof Date) {

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            return sdf.format(value);
        }

        if (value instanceof Timestamp) {

            SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
            return sdf.format(value);
        }

        if (value instanceof LocalDateTime) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return dtf.format((LocalDateTime) value);
        }

        if (value instanceof LocalDate) {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return dtf.format((LocalDate) value);
        }

        return value.toString();
    }

}
