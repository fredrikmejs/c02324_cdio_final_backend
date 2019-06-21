package rest;

public class DTS {

    /**
     * Finds the correct date for the items we put in the freezer.
     * @param days Days comes from the freezer_expiration in the date base.
     * @return Returns the expiration date of the food item.
     */
    public static long addDays(int days) {

        return System.currentTimeMillis() + (days*(1000*60*60*24));
    }
}
