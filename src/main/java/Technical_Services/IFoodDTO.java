package Technical_Services;

import Technical_Services.ECategory;
import Technical_Services.ELocation;

import java.lang.annotation.ElementType;
import java.sql.Date;

public interface IFoodDTO {


    //Expiration Dates
    Date getExpDate();
    void setExpDate(Date date);


    //TODO: Amount - redacted for now

    //TODO: Unit - redacted for now

    //ID
    int getFoodId();
    void setFoodId(int foodId);


    //Name
    String getName();
    void setName(String name);


    //Location
    //TODO: Create enum for location (Freezer, Fridge, Pantry)
    ELocation getLocation();
    void setLocation(ELocation location);


    //Category
    //TODO: Create enum for category (Fish, meats, vegetables etc.)
    ECategory getCategory();
    void setCateGory(ECategory category);

}
