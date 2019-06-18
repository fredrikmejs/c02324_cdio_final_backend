package Backend;

import Technical_Services.ECategory;
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
    IFoodDTO readFood(String userName, int foodID)throws SQLException;

    //Update food
    boolean updateFood(IFoodDTO food) throws SQLException;

    //Delete food
    boolean deleteFood(int foodId, String userName) throws SQLException;

    //Delete all foods
    boolean deleteAllFoods(String userName, ELocation location) throws SQLException;

    //gets a list of food that is expiring
    List<IFoodDTO> getExpiredFood(int days, String userName) throws SQLException;

    //Connection
    void closeConnection() throws SQLException;
    void createConnection() throws SQLException;

    //Last ID
    int getLastID(String userName) throws SQLException;

    //User
    boolean authenticateUser(String userName)throws SQLException;
    boolean deleteUser(String userName)throws SQLException;
}
