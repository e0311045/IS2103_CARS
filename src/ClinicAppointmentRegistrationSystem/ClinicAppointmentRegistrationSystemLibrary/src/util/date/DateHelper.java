/*
 * Group 11 IS2103 Pair Project
 * Group members:
 * - Gerwin Lee , A0184250L 
 * - Ng Shei Er , A0185574R
 * 
 */
package util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import util.exception.InvalidDateTimeFormatException;


public class DateHelper {

    public static SimpleDateFormat dateSDF = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm");
    public static SimpleDateFormat daySDF = new SimpleDateFormat("EEEE");
    private static Date currentDate;

    public static Date convertToDate(String dateStr) throws InvalidDateTimeFormatException {
        Date date = new Date();

        try {
            date = dateSDF.parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateTimeFormatException("Wrong Date Format. Please ensure that you input in the format " + dateSDF.toString() + " e.g. (2019-10-20)");
        }

        return date;
    }

    
    public static Date convertToTime(String dateStr) throws InvalidDateTimeFormatException {
        Date date = new Date();

        try {
            date = timeSDF.parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateTimeFormatException("Wrong Date Format. Please ensure that you input in the format " + timeSDF.toString() + " e.g. (13:10)");
        }

        return date;
    }

    public static long dateDiff(Date d1, Date d2) {
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

    public static int getWeekNo(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }
    
    public static String getDayOfWeek(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return daySDF.format(cal.get(Calendar.DAY_OF_WEEK));
    }


    public static Date getCurrentDate() {
        return currentDate;
    }

    public static void setCurrentDate(Date aCurrentDate) {       
        currentDate = aCurrentDate;
    }
    
    public static int getClosingHour(String dayOfWeek)
    {
        switch (dayOfWeek) {
            case "Monday":
            case "Tuesday":
            case "Wednesday":
                return 17;
            case "Thursday":
                return 16;
            default:
                return 17;
        }                
    }
    
    public static int getClosingMinute(String dayOfWeek)
    {
        switch (dayOfWeek) {
            case "Monday":
            case "Tuesday":
            case "Wednesday":
            case "Friday":
                return 30;
            default:
                return 00;
        }                
    }
}

