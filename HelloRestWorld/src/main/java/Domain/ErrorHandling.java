package Domain;

import Backend.*;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;
import java.sql.SQLException;

public class ErrorHandling implements IErrorHandling {

    private Backend backend = new Backend();
    private DataLayer dataLayer = new DataLayer();

    public boolean addFood(IFoodDTO food) throws SQLException {

        checkCategory(food);
        checkLocation(food);
        backend.createFood(food);
        dataLayer.addFood(food);

        return true;
    }

    public boolean updateFood(IFoodDTO foodDTO) throws SQLException {

//        checkCategory(oldFood);
//        checkLocation(oldFood);
//        backend.updateFood(newFood);
//        dataLayer.updateFood(oldFood,newFood);
          backend.updateFood(foodDTO);

        return true;
    }

    public boolean deleteFood(IFoodDTO food) throws SQLException {

        checkLocation(food);
        checkCategory(food);

        backend.deleteFood(food.getID(),food.getUserName());
        dataLayer.deleteFood(food);

        return false;
    }

    public boolean deleteAll(ELocation location, String userName, IFoodDTO food) throws SQLException {


        checkLocation(food);
        backend.deleteAllFoods(userName,location);
        dataLayer.deleteAll(location,userName);

        return false;
    }

    private boolean checkCategory(IFoodDTO food) {
        for (ECategory c: ECategory.values()) {
            if (c.equals(food.getCategory()))
                return true;
        }
        return false;
    }

    private boolean checkLocation(IFoodDTO food) {
        for (ELocation l: ELocation.values()) {
            if (l.equals(food.getLocation())) {
                return true;
            }
        }
        return false;
    }
    public boolean getFoodList(String userName) throws SQLException{
        boolean success = true;
        try {

            backend.getFoodList(userName); throw new Exception();

        } catch (Exception e) {
            success = false;
        }

        return success;
    }

    public boolean getFoodItem(String userName, int id) throws SQLException {

        backend.readFood(userName,id);

        return false;
    }
}