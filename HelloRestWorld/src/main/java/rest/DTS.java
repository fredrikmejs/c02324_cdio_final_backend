package rest;

import java.sql.Date;
import java.time.LocalDate;

public class DTS {
    public static String dateToString(int day, int month, int year){
        char[] dates = LocalDate.of(year, month, day).toString().toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dates[8]);
        stringBuilder.append(dates[9]);
        stringBuilder.append(dates[7]);
        stringBuilder.append(dates[5]);
        stringBuilder.append(dates[6]);
        stringBuilder.append(dates[4]);
        for(int i = 0; i < 4; i++){
            stringBuilder.append(dates[i]);
        }
        return stringBuilder.toString();
    }

    public Date addDays(Date day, int days){
        return new Date(day.getTime() + (days*(1000*60*60*24)));//time for given day * (millis/sec*sec/min*min/hr*hr/day)
    }
}
