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
//    public static Calendar walkIn = Calendar.getInstance();

    public static Date convertToDate(String dateStr) throws InvalidDateTimeFormatException {
        Date date = new Date();

        try {
            date = dateSDF.parse(dateStr);
        } catch (ParseException e) {
            throw new InvalidDateTimeFormatException("Wrong Date Format. Please ensure that you input in the format " + dateSDF.toString() + " e.g. (2020-04-06)");
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
    
    public static int getClosingHour(String dayOfWeek)
    {
        switch (dayOfWeek) {
            case "Monday":
            case "Tuesday":
            case "Wednesday":
                return 18;
            case "Thursday":
                return 16;
            default:
                return 17;
        }                
    }
    
    public static int getClosingMinute(String dayOfWeek)
    {
        switch (dayOfWeek) {
            case "Friday":
                return 30;
            default:
                return 0;
        }                
    }
    
    public static boolean isOperational(Calendar walkIn){
        int closingHour = getClosingHour(getDayOfWeek(walkIn.getTime()));
        int closingMin = getClosingMinute(getDayOfWeek(walkIn.getTime()));
        return (walkIn.get(Calendar.HOUR_OF_DAY) == 8 && walkIn.get(Calendar.MINUTE) >= 30) || (walkIn.get(Calendar.HOUR_OF_DAY) == closingHour && walkIn.get(Calendar.MINUTE) < closingMin);
    }
    
    public static boolean isBreakTime(Calendar walkIn){
        return (walkIn.get(Calendar.HOUR_OF_DAY) == 12 && walkIn.get(Calendar.MINUTE) >=30 || (walkIn.get(Calendar.HOUR_OF_DAY) == 13 && walkIn.get(Calendar.MINUTE) < 30));
    }
    
    public static String[] getAvailabilitySlots(Date inputDate){
        int closingHour = getClosingHour(getDayOfWeek(inputDate));
        int closingMin = getClosingMinute(getDayOfWeek(inputDate));
        String day = getDayOfWeek(inputDate);
        int slots = 0;
        
        switch (day) {
            case "Monday":
            case "Tuesday":
            case "Wednesday":
                slots = 18;
            case "Thursday":
                slots = 16;
            default:
                slots = 17;
        } 
        
        Calendar cal = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        String[] availability = new String[slots];

        long ONE_MINUTE_IN_MILLIS = 60000;
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 30);

        long t = cal.getTimeInMillis();
        Date tempTime = new Date(t);

        for (int i = 0; i < slots; i++) { // an array of time slots from 0830 to closing Time of the day
            if(t==46800000){
                t += 60 * ONE_MINUTE_IN_MILLIS;
                
            } 
            availability[i] = DateHelper.timeSDF.format(tempTime);
            t += 30 * ONE_MINUTE_IN_MILLIS;
            tempTime = new Date(t);        
        }
        return availability;
    }
    public static void prepareAvailabilityWalkInSlots(Calendar walkIn,String[][] availability){
            availability[0][0] = "Time ";
            
            // doctorid in table row 0
            for (int i = 1; i < availability[0].length + 1; i++) {
                availability[0][i] = Integer.toString(i);
            }
            

            long ONE_MINUTE_IN_MILLIS = 60000;
            int hour = walkIn.get(Calendar.HOUR_OF_DAY);
            int min = walkIn.get(Calendar.MINUTE);

            if (min >= 00 && min <= 29) {
                walkIn.set(Calendar.MINUTE, 30);
            } else {
                walkIn.set(Calendar.HOUR_OF_DAY, hour + 1);
                walkIn.set(Calendar.MINUTE, 00);
            }

            long t = walkIn.getTimeInMillis();

            // time in table column 0
            for (int i = 1; i < 7; i++) {
                if(t==46800000){ //Check if timeslot is 12:30
                    t += 60 * ONE_MINUTE_IN_MILLIS; 
                }
                Date time = new Date(t);
                availability[i][0] = DateHelper.timeSDF.format(time);
                t += 30 * ONE_MINUTE_IN_MILLIS;              
            }
            
            // create all empty sets first to avoid null exception in accessing array
            for (int i = 1; i < 7; i++) {
                for (int j = 1; j < availability[0].length + 1; j++) {
                    availability[i][j] = "O"; // open slot
                }
            }
    }
}

