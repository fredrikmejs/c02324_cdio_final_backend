package Backend;

import java.sql.Date;

public interface IFoodDTO {
    //Expiration Dates
    void setExpDate(Date date);
    Date getExpDate();
    //TODO: Amount - redacted for now

    //TODO: Unit - redacted for now

    //Name
    void setName(String name);
    String getName();

    //Location
    //TODO: Create enum for location (Freezer, Fridge, Pantry)
    void setLocation(ELocation location);
    ELocation getLocation();

    //Category
    //TODO: Create enum for category (Fish, meats, vegetables etc.)
    void setCategory(ECategory category);
    ECategory getCategory();
}
