package io.blacktoast.utils.bean;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static Date getNow() {
		return new Date((new Timestamp(System.currentTimeMillis()).getTime()));
	}
	
	public static String getFormatedTimestamp(String dateStr, String fromPattern, String toPattern)
			throws ParseException {
		DateFormat fromFormat = new SimpleDateFormat(fromPattern);
		DateFormat toFormat = new SimpleDateFormat(toPattern);
		Date date = fromFormat.parse(dateStr);
		return toFormat.format(date);
	}

	public static String getFormatedTimestamp(Object dateObj, String toPattern) {
		DateFormat toFormat = new SimpleDateFormat(toPattern);
		return toFormat.format(dateObj);
	}

	public static Date getParsedDate(String dateStr, String fromPattern) throws ParseException {
		DateFormat fromFormat = new SimpleDateFormat(fromPattern);
		return fromFormat.parse(dateStr);
	}

	public static Date getParsedDate(String dateStr, String fromPattern, Date resultError) {
		DateFormat fromFormat = new SimpleDateFormat(fromPattern);
		fromFormat.setLenient(false);
		try {
			return fromFormat.parse(dateStr);

		} catch (Exception e) {
			return resultError;
		}
	}

	public static LocalDateTime toLocalDateTime(Date date, LocalDateTime resultError) {
		try {

			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		} catch (Exception e) {
			return resultError;
		}
	}

	public static java.sql.Date toSqlDate(LocalDateTime date) {
		try {
			return java.sql.Date.valueOf(date.toLocalDate());

		} catch (Exception e) {
			return null;
		}
	}

	public static LocalDateTime toLocalDateTime(java.sql.Date date) {
		try {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		} catch (Exception e) {
			return null;
		}

	}

	public static LocalDateTime toLocalDateTime(Timestamp date) {
		try {
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		} catch (Exception e) {
			return null;
		}

	}

	public static Timestamp toTimestamp(LocalDateTime date) {
		try {
			return Timestamp.valueOf(date);

		} catch (Exception e) {
			return null;
		}
	}

	public static LocalDateTime parsedToLocalDateTime(String dateStr, String fromPattern) {
		Date date = getParsedDate(dateStr, fromPattern, null);
		return toLocalDateTime(date, null);
	}

}
