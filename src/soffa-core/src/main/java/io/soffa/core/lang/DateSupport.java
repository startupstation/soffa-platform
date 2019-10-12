package io.soffa.core.lang;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
import io.soffa.core.exception.TechnicalException;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Seconds;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateSupport {

    private DateSupport() {
    }

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_mm = "yyyy-MM-dd HH:mm";
    public static final String DD_MMM_YYYY_SPACED = "dd MMMM yyyy";
    public static final String DD_MMM_YYYY = "dd-MM-yyyy";

    private static Parser parser = new Parser();

    public static Date parse(String input) {
        List<DateGroup> d = parser.parse(input);
        if (d.isEmpty()) {
            try {
                return DateUtils.parseDate(input);
            } catch (ParseException e) {
                throw new TechnicalException(e.getMessage(), e);
            }
        }
        return d.get(0).getDates().get(0);
    }

    public static String format(Date date) {
        return format(date, YYYY_MM_DD);
    }

    public static String format(Date date, String format) {
        if (date == null) return null;
        return new SimpleDateFormat(format, Locale.FRANCE).format(date);
    }

    public static Boolean isNowOrLater(Date date) {
        DateTime dt = new DateTime(date);
        return dt.isAfterNow() || dt.isEqualNow() || dt.isAfter(DateTime.now().minusSeconds(30));
    }

    public static boolean isBeforeMonths(Date date, int months) {
        return new DateTime(date).isBefore(DateTime.now().plusMonths(months));
    }

    public static boolean isBeforeNow(Date date) {
        return new DateTime(date).isBeforeNow();
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }


    public static int getMinute(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MINUTE);
    }

    public static Date plusMonths(Date date, int months) {
        return new DateTime(date).plusMonths(months).toDate();
    }

    public static Date minusMonths(Date date, int months) {
        return new DateTime(date).minusMonths(months).toDate();
    }

    public static Date plusDays(Date date, int days) {
        return new DateTime(date).plusDays(days).toDate();
    }

    public static Date plusWeeks(Date date, int weeks) {
        return new DateTime(date).withDayOfWeek(DateTimeConstants.MONDAY).plusWeeks(weeks).toDate();
    }

    public static Date plusMinutes(Date date, int minutes) {
        return new DateTime(date).withSecondOfMinute(0).plusMinutes(minutes).toDate();
    }

    public static Date plusHours(Date date, int hours) {
        return new DateTime(date).withSecondOfMinute(0).plusHours(hours).toDate();
    }

    public static int secondsBetween(Date date1, Date date2) {
        return Seconds.secondsBetween(new DateTime(date1), new DateTime(date2)).getSeconds();
    }

}
