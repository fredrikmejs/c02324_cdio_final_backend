package rest;

import java.util.concurrent.TimeUnit;

public class DTS {

    /**
     * Finds the correct date for the items we put in the freezer.
     * @param days Days comes from the freezer_expiration in the date base.
     * @return Returns the expiration date of the food item.
     */
    public static long addDays(int days) {
        long currentDay = System.currentTimeMillis();
        long totalTime = currentDay + TimeUnit.DAYS.toMillis(days);
        return totalTime;
    }
}
