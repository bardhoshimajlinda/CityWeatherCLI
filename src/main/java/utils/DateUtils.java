package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getDateWithMidnightTime(Date date) {
        if (date == null) return null;
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Date getDateWithDayStartTime(Date date) {
        if (date == null) return null;

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static SimpleDateFormat getMinusDelimeterdateFormat(){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm");
    }
}
