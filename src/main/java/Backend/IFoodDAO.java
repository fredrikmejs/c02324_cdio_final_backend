package Backend;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IFoodDAO {
    //CRUD

    //Create food
    boolean createFood(IFoodDTO food) throws SQLException;

    //Read food
    List readFoods(String name) throws SQLException;

    //Update food
    boolean updateFood(IFoodDTO food) throws SQLException;

    //Delete food
    boolean deleteFood(int foodId) throws SQLException;

    //Delete all foods
    boolean deleteAllFoods(ELocation location, String userName) throws SQLException;
}
