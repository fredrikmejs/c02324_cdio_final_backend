package Technical_Services;

public interface IFoodDTO {

    //Food ID
    int getID();
    void setID(int food_id);

    //Expiration Dates
    long getExpDate();
    void setExpDate(long timeSinceEpochInMillis);



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
