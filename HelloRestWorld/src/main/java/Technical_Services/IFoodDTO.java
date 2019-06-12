package Technical_Services;

import java.sql.Date;

public interface IFoodDTO {

    //Food ID
    int getID();
    void setID(int food_id);

    //Expiration Dates
    Date getExpDate();
    void setExpDate(Date date);

    //TODO: Unit - redacted for now

    //Food name
    String getFoodName();
    void setFoodName(String name);


    //Location
    //TODO: Create enum for location (Freezer, Fridge, Pantry)
    ELocation getLocation();
    void setLocation(ELocation location);


    //Category
    //TODO: Create enum for category (Fish, meats, vegetables etc.)
    ECategory getCategory();
    void setCategory(ECategory category);


    //User name
    String getUserName();
    void setUserName(String userName);


}
