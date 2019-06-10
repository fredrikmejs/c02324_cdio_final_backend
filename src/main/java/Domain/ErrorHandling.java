package Domain;

import Backend.*;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;
import java.sql.SQLException;

public class ErrorHandling implements IErrorHandler {

private Backend backend = new Backend();
private DataLayer dataLayer = new DataLayer();

    public boolean addFood(IFoodDTO food) throws SQLException {

        checkCategory(food);
        checkLocation(food);
        backend.createFood(food);
        dataLayer.addFood(food);

        return true;
    }

    public boolean updateFood(IFoodDTO oldFood, IFoodDTO newFood) throws SQLException {

        checkCategory(oldFood);
        checkLocation(oldFood);
        backend.updateFood(newFood);
        dataLayer.updateFood(oldFood,newFood);

        return true;
    }

    public boolean deleteFood(IFoodDTO food) throws SQLException {

        checkLocation(food);
        checkCategory(food);

        backend.deleteFood(food.getName(),food.getExpDate());
        dataLayer.deleteFood(food);

        return false;
    }

    public boolean deleteAll(ELocation location, IFoodDTO food) {


        checkLocation(food);
        backend.deleteAllFoods(location);
        dataLayer.deleteAll(location);

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


}