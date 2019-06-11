package Backend;

import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.util.ArrayList;
import java.util.Date;

public class DataLayer implements IDataLayer {
    private ArrayList<IFoodDTO> foodList = new ArrayList<IFoodDTO>();

    /**
     * Method fot adding a FoodDTO to a list of FoodDTO items.
     * @param food The FoodDTO object that should be added to the list of food items.
     */
    public void addFood(IFoodDTO food) {
        foodList.add(food);
    }

    /**
     * Method for finding a specific FoodDTO in the list.
     * @param name The name of the item, used to for finding unique objects in unison with expDate.
     * @param expDate The expiration date of the item.
     * @return
     */
    public IFoodDTO readFood(String name, Date expDate) {
        for (int i = 0; i <foodList.size() ; i++) {
            if(foodList.get(i).getName().equals(name) && foodList.get(i).getExpDate().equals(expDate))
                return foodList.get(i);
        }
        return null;
    }

    /**
     * Method for updating an item in the list, where it is set to be a new FoodDTO.
     * @param oldFood The old version of the item, which is to be updated.
     * @param newFood The new version of the item, which should be set as.
     */
    public void updateFood(IFoodDTO oldFood, IFoodDTO newFood) {
        int pos = foodList.indexOf(oldFood);
        foodList.set(pos, newFood);
    }

    /**
     * Method for deleting one food item.
     * @param food the item which is to be deleted.
     */
    public void deleteFood(IFoodDTO food) {
        foodList.remove(food);
    }

    /**
     * THis methods deletes all entries from the desired location.
     * @param location The location of deletion.
     */
    public void deleteAll(ELocation location) {

        int size = foodList.size();

        for (int i = size-1; i >= 0; i--) {
            if (foodList.get(i).getLocation().equals(location) || location.equals(ELocation.All))
                foodList.remove(i);
        }
    }
    /**
     * For testing purposes atm.
     * @return This list.
     */
    public ArrayList<IFoodDTO> getFoodList() {
        return foodList;
    }
}