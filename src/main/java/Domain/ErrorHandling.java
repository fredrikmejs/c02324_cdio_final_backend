package Domain;

import Backend.Backend;
import Technical_Services.ECategory;
import Technical_Services.IFoodDTO;

public class ErrorHandling implements IErrorHandler {

Backend backend = new Backend();

    public boolean addFood(IFoodDTO food) {

        return false;
    }

    public boolean updateFood(IFoodDTO oldFood) {
        return false;
    }

    public boolean deleteFood(IFoodDTO Food) {
        return false;
    }

    public boolean deleteAll() {
        return false;
    }
}
