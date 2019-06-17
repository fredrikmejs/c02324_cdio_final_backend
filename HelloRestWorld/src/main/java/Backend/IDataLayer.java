package Backend;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.util.Date;

public interface IDataLayer {
    void addFood(IFoodDTO food);

    IFoodDTO readFood(String userName, int id);

    void updateFood(IFoodDTO newFood);

    void deleteFood(String userName, int id);

    void deleteAll(ELocation location, String userName);

    IFoodDTO readFood(int foodID, String userName);
}