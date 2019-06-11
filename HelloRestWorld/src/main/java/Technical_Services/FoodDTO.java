package Technical_Services;

import java.sql.Date;

public class FoodDTO implements IFoodDTO {

    private Date expDate;
    private String name;
    private ELocation location;
    private ECategory category;
    private int food_id;
    private double amount;
    private String userName;


public FoodDTO(String foodName, Date date, ELocation location, ECategory category, double amount, String userName){
    this.expDate = date;
    this.name = foodName;
    this.category = category;
    this.location = location;
    this.amount = amount;
    this.userName = userName;

}

    public FoodDTO(int food_id, String foodName, Date date, ELocation location, ECategory category, double amount, String userName){
        this.expDate = date;
        this.name = foodName;
        this.category = category;
        this.location = location;
        this.amount = amount;
        this.userName = userName;
        this.food_id = food_id;
    }
public FoodDTO(){}

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date date) {
    this.expDate = date;
    }

    public String getFoodName(){
        return name;
    }

    public void setName(String name) {
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
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
    this.amount = amount;
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

 /*   public String toString() {
        return "FoodList [Food id = " + food_id + ", Food name = " + name+ ", Expiring Date = " + expDate + ", location = " + location + ", Category = "+ category + ", Amount = " + amount + ", Username = " + userName +"]\n";
    }
*/
}