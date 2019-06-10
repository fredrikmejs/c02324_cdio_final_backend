package Backend;

import Technical_Services.ECategory;
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
    boolean updateFood(ECategory category, IFoodDTO food) throws SQLException;
    boolean updateFood(Date expDate, IFoodDTO food) throws SQLException;
    //TODO: Consider doing location update
    boolean updateFood(ECategory category, Date expDate, IFoodDTO food) throws SQLException;

    //Delete food
    boolean deleteFood(String name, Date expDate) throws SQLException;

    //Delete all foods
    boolean deleteAllFoods(ELocation location);
}