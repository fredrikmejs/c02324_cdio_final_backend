package Technical_Services;

import java.sql.Date;

public interface IFoodDTO {


    int getID();
    void setID(int food_id);

    //Expiration Dates
    Date getExpDate();
    void setExpDate(Date date);


    //TODO: Amount - redacted for now

    //TODO: Unit - redacted for now

    //Name
    String getFoodName();
    void setName(String name);


    //Location
    //TODO: Create enum for location (Freezer, Fridge, Pantry)
    ELocation getLocation();
    void setLocation(ELocation location);


    //Category
    //TODO: Create enum for category (Fish, meats, vegetables etc.)
    ECategory getCategory();
    void setCategory(ECategory category);

    double getAmount();
    void setAmount(double amount);

    String getUserName();
    void setUserName(String userName);


}
