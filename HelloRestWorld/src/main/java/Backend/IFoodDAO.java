package Backend;

import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IFoodDAO {
    //CRUD

    //Create food
    boolean createFood(IFoodDTO food) throws SQLException;

    //Read food
    List<IFoodDTO> getFoodList(String name) throws SQLException;

    //Update food
    boolean updateFood(IFoodDTO food) throws SQLException;

    //Delete food
    boolean deleteFood(int foodId, String userName) throws SQLException;

    //Delete all foods
    boolean deleteAllFoods(String userName, ELocation location) throws SQLException;

}
