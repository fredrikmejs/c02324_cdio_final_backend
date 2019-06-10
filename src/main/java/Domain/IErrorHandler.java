package Domain;

import Technical_Services.IFoodDTO;

public interface IErrorHandler {

    boolean addFood(IFoodDTO food);

    boolean updateFood(IFoodDTO oldFood);

    boolean deleteFood(IFoodDTO Food);

    boolean deleteAll();

}
