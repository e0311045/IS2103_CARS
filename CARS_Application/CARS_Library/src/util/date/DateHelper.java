/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public static SimpleDateFormat dateTimeSDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static SimpleDateFormat timeSDF = new SimpleDateFormat("HH:mm");

    public static Date convertToDate(String dateStr) throws InvalidDateTimeFormatException {
        Date date = new Date();

        try {
            date = dateSDF.parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateTimeFormatException("Wrong Date Format. Please ensure that you input in the format " + dateSDF.toString() + " e.g. (2019-10-20)");
        }

        return date;
    }

    public static Date convertToDateTime(String dateStr) throws InvalidDateTimeFormatException {
        Date date = new Date();

        try {
            date = dateTimeSDF.parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateTimeFormatException("Wrong Date Format. Please ensure that you input in the format " + dateTimeSDF.toString() + " e.g. (2019-10-20 13:10)");
        }

        return date;
    }
    
    public static Date convertToTime(String dateStr) throws InvalidDateTimeFormatException {
        Date date = new Date();

        try {
            date = timeSDF.parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateTimeFormatException("Wrong Date Format. Please ensure that you input in the format " + dateTimeSDF.toString() + " e.g. (13:10)");
        }

        return date;
    }

    public static long dateDiff(Date d1, Date d2) {
        long diffInMillies = Math.abs(d1.getTime() - d2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }

    public static Date addHoursToDate(Date date, int hour) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);
        
        return cal.getTime();
    }
}
