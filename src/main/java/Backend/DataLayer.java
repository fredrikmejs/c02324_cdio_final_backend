package Backend;

import Technical_Services.IFoodDTO;
import Technical_Services.foodDTO;

import java.util.ArrayList;
import java.util.Date;

public class DataLayer implements IDataLayer {
    private ArrayList<IFoodDTO> foodList = new ArrayList<IFoodDTO>();
    public void addFood(IFoodDTO food) {
        foodList.add(food);
    }

    public IFoodDTO readFood(String name, Date expDate) {
        for (int i = 0; i <foodList.size() ; i++) {
            if(foodList.get(i).getName().equals(name) && foodList.get(i).getExpDate().equals(expDate))
                return foodList.get(i);
        }
        return null;
    }

    public void updateFood(IFoodDTO oldFood, IFoodDTO newFood) {
        int pos = foodList.indexOf(oldFood);
        foodList.set(pos, newFood);
    }

    public void deleteFood(IFoodDTO food) {
        foodList.remove(food);
    }

    public void deleteAll() {
        foodList.clear();
    }
}
