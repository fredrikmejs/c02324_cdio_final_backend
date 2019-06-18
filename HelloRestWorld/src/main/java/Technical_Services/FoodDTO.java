package Technical_Services;

import com.sun.org.apache.xpath.internal.operations.Equals;

import java.sql.Date;

public class FoodDTO extends Equals implements IFoodDTO {

    private Date expDate;
    private String name;
    private ELocation location;
    private ECategory category;
    private int food_id;
    private String userName;


public FoodDTO(String foodName, Date date, ELocation location, ECategory category, String userName){
    this.expDate = new Date(date.getTime() + (1000*60*60*12));
    this.name = foodName;
    this.category = category;
    this.location = location;
    this.userName = userName;

}

    public FoodDTO(int food_id, String foodName, Date date, ELocation location, ECategory category, String userName){
        this.expDate = new Date(date.getTime() + (1000*60*60*12));
        this.name = foodName;
        this.category = category;
        this.location = location;
        this.userName = userName;
        this.food_id = food_id;
    }
public FoodDTO(){}

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date date) {
    this.expDate = new Date(date.getTime() + (1000*60*60*12)); //Add 12 hours to day to hopefully fix off by 1 day because of DST
    }

    public String getFoodName(){
        return name;
    }

    public void setFoodName(String name) {
    this.name = name;
    }

    public ELocation getLocation() {
        return location;
    }

    public void setLocation(ELocation location) {
    this.location = location;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
    this.category = category;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
    this.userName = userName;
    }

    public void setID(int id){
        this.food_id = id;
    }

    public int getID(){
        return food_id;
    }

    public String toString() {
        return "FoodList [Food id = " + food_id + ", Food name = " + name+ ", expiration date = " + expDate +
                ", location = " + location + ", Category = "+ category + ", Username = " + userName +"]\n";
    }

    @Override
    public boolean equals (Object other){
        if (!other.getClass().isInstance(this)) return false;
        if( !((FoodDTO) other).getFoodName().equals(this.getFoodName())) return false;
        if( !((FoodDTO) other).getExpDate().toString().equals(this.getExpDate().toString())) return false;
        if( ((FoodDTO) other).getCategory() != this.getCategory()) return false;
        if( ((FoodDTO) other).getLocation() != this.getLocation()) return false;
        if( ((FoodDTO) other).getUserName() != this.getUserName()) return false;

        return true;

    }

}