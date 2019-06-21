package rest;

import java.sql.Date;
import java.time.LocalDate;

public class DTS {

    /**
     * Finds the correct date for the items we put in the freezer.
     * @param days Days comes from the freezer_expiration in the date base.
     * @return Returns the expiration date of the food item.
     */
    public static Date addDays(int days) {

        Date date = Date.valueOf(LocalDate.now().plusDays(days));
        return date;//time for given day * (millis/sec*sec/min*min/hr*hr/day)
    }
}
