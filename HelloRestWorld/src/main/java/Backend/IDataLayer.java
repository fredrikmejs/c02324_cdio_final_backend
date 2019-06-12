package Backend;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.util.Date;

public interface IDataLayer {
    void addFood(IFoodDTO food);

    IFoodDTO readFood(String name, Date expDate);

    void updateFood(IFoodDTO oldFood, IFoodDTO newFood);

    void deleteFood(IFoodDTO food);

    void deleteAll(ELocation location, String userName);
}