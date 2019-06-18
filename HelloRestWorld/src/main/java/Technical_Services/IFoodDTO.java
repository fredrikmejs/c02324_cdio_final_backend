package Technical_Services;

import java.sql.Date;

public interface IFoodDTO {

    //Food ID
    int getID();
    void setID(int food_id);

    //Expiration Dates
    Date getExpDate();
    void setExpDate(Date date);



    //Food name
    String getFoodName();
    void setFoodName(String name);


    //Location
    ELocation getLocation();
    void setLocation(ELocation location);


    //Category
    ECategory getCategory();
    void setCategory(ECategory category);


    //User name
    String getUserName();
    void setUserName(String userName);

    //toString
    String toString();

    //equals
    boolean equals(Object object);


}
