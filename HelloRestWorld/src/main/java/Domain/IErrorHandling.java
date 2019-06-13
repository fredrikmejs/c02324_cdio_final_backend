package Domain;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.sql.SQLException;
import java.util.List;

public interface IErrorHandling {

    boolean addFood(IFoodDTO food) throws SQLException;

    boolean updateFood(IFoodDTO foodDTO) throws SQLException;

    boolean deleteFood(String userName, int id) throws SQLException;

    boolean deleteAll(ELocation location, String userName) throws SQLException;

    List<IFoodDTO> getFoodList(String userName) throws SQLException;

    IFoodDTO getFoodItem(String userName, int id)throws SQLException;

}