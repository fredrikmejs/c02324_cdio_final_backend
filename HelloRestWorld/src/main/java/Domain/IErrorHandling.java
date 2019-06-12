package Domain;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.sql.SQLException;
import java.util.List;

public interface IErrorHandling {

    boolean addFood(IFoodDTO food) throws SQLException;

    boolean updateFood(IFoodDTO foodDTO) throws SQLException;

    boolean deleteFood(IFoodDTO food) throws SQLException;

    boolean deleteAll(ELocation location, String userName, IFoodDTO food) throws SQLException;

    List<IFoodDTO> getFoodList(String userName) throws SQLException;

    IFoodDTO getFoodItem(String userName, int id)throws SQLException;

}