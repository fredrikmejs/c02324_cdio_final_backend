package Backend;

import java.sql.Date;
import java.util.List;

public interface IFoodDAO {
    //CRUD

    //Create food
    boolean createFood(IFoodDTO food);

    //Read food
    List readFoods();

    //Update food
    boolean updateFood(ECategory category);
    boolean updateFood(Date expDate);
    //TODO: Consider doing location update
    boolean updateFood(ECategory category, Date expDate);

    //Delete food
    boolean deleteFood(String name, Date expDate);

    //Delete all foods
    boolean deleteAllFoods(ELocation location);
}
