package Technical_Services;

import com.sun.org.apache.xpath.internal.operations.Equals;

import java.sql.Date;

/**
 * Class is here to manage the food object and all of its properties
 */
public class FoodDTO extends Equals implements IFoodDTO {
//Define variables
    private long expDate;
    private String name;
    private ELocation location;
    private ECategory category;
    private int food_id;
    private String userName;

    /**
     * An empty constructor for use with REST
     */
    public FoodDTO(){}
    /**
     * Method gets the expiration date of the food
     * @return An SQL date which represents the expiration date
     */
    public long getExpDate() {
        return expDate;
    }

    /**
     * Method sets the expiration date
     * @param timeSinceEpochInMillis
     */
    public void setExpDate(long timeSinceEpochInMillis) {
        this.expDate = timeSinceEpochInMillis + (1000*60*60*3); //Add 12 hours to day to hopefully fix off by 1 day because of DST
    }

    /**
     * Method gets name of the food.
     * @return Name of food as String.
     */
    public String getFoodName(){
        return name;
    }

    /**
     * Method sets the name of the food
     * @param name String of the name
     */
    public void setFoodName(String name) {
        this.name = name;
    }

    /**
     * Method gets the location of the food
     * @return Returns the location of the food (ELocation).
     */
    public ELocation getLocation() {
        return location;
    }

    /**
     * Method sets the location of the food.
     * @param location The location of the food.
     */
    public void setLocation(ELocation location) {
        this.location = location;
    }

    /**
     * Method gets the category of the food.
     * @return Returns the category of the food.
     */
    public ECategory getCategory() {
        return category;
    }

    /**
     * Method sets the category of the food.
     * @param category The category of the food.
     */
    public void setCategory(ECategory category) {
        this.category = category;
    }

    /**
     * Constructor to create a food item without an ID
     * @param foodName Name of the food.
     * @param date The expiration date of the food.
     * @param location The location of the food.
     * @param category The category of which the food belongs.
     * @param userName The user name of the user who owns the food.
     */
    public FoodDTO(String foodName, long date, ELocation location, ECategory category, String userName){
//      Variables are initialised
    this.expDate = date + (1000*60*60*3);//The supplied date is added 12 additional hours to compensate for DST and MySQL incompatibility
    this.name = foodName;
    this.category = category;
    this.location = location;
    this.userName = userName;

}

    /**
     * Constructor to create a food item
     * @param food_id ID of the food.
     * @param foodName Name of the food.
     * @param date The expiration date of the food.
     * @param location The location of the food.
     * @param category The category of which the food belongs.
     * @param userName The user name of the user who owns the food.
     */
    public FoodDTO(int food_id, String foodName, long date, ELocation location, ECategory category, String userName){
//        Variables are now initialised
        this.expDate = date + (1000*60*60*3);//12 hours are added to fix compatibility issues
        this.name = foodName;
        this.category = category;
        this.location = location;
        this.userName = userName;
        this.food_id = food_id;
    }





    /**
     * Method gets the userName of the foods (its owner)
     * @return Who owns the food as a String.
     */
    @Override
    public String getUserName() {
        return userName;
    }

    /**
     * Method sets the userName of the food.
     * @param userName Name of the user to own the food.
     */
    @Override
    public void setUserName(String userName) {
    this.userName = userName;
    }

    /**
     * Method sets the ID of the food
     * @param id ID of the food
     */
    public void setID(int id){
        this.food_id = id;
    }

    /**
     * Method gets the ID of the food
     * @return The food ID
     */
    public int getID(){
        return food_id;
    }

    /**
     * Method converts the food object to a String representation
     * @return Food object as a String.
     */
    public String toString() {
        return "FoodList [Food id = " + food_id + ", Food name = " + name+ ", expiration date = " + expDate +
                ", location = " + location + ", Category = "+ category + ", Username = " + userName +"]";
    }

    /**
     * Method is here to enable comparison of food objects.
     * @param other This is the other food object to be compared with.
     * @return True if the objects are equal, else false.
     */
    @Override
    public boolean equals (Object other){
        if (!other.getClass().isInstance(this)) return false;
        if( !((FoodDTO) other).getFoodName().equals(this.getFoodName())) return false;
        if( ((FoodDTO) other).getExpDate() != this.getExpDate()) return false;
        if( ((FoodDTO) other).getCategory() != this.getCategory()) return false;
        if( ((FoodDTO) other).getLocation() != this.getLocation()) return false;
        return ((FoodDTO) other).getUserName().equals(this.getUserName());

    }

}