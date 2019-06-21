package rest;

import java.sql.Date;
import java.time.LocalDate;

public class DTS {

    public static Date addDays(int days){

        Date date = Date.valueOf(LocalDate.now().plusDays(days));
        return date;//time for given day * (millis/sec*sec/min*min/hr*hr/day)
    }
}
