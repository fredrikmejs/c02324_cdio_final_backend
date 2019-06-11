package Domain;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.sql.SQLException;

public interface IErrorHandler {

    boolean addFood(IFoodDTO food) throws SQLException;

    boolean updateFood(IFoodDTO oldFood, IFoodDTO newFood) throws SQLException;

    boolean deleteFood(IFoodDTO food) throws SQLException;

    boolean deleteAll(ELocation location, IFoodDTO food, String userName) throws SQLException;

}