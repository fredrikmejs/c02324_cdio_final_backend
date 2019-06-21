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

    /**
     * Finds the correct date for the items we put in the freezer.
     * @param days Days comes from the freezer_expiration in the date base.
     * @return Returns the expiration date of the food item.
     */
    public static Date addDays(int days){
        //takes the current date and adds the days given from the freezer_expiration in the database
        return Date.valueOf(LocalDate.now().plusDays(days));
    }
}
