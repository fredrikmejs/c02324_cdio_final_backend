package Backend;

import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class DataLayer implements IDataLayer {
    private ArrayList<IFoodDTO> foodList = new ArrayList<>();

    /**
     * Method fot adding a FoodDTO to a list of FoodDTO items.
     * @param food The FoodDTO object that should be added to the list of food items.
     */
    public void addFood(IFoodDTO food) {
        int id;
        if (foodList.size() == 0){
        id = 1;
        }else
        id = foodList.get(foodList.size()-1).getID()+1;
        //test +=1;
        //int id = foodList.size();
        foodList.add(new FoodDTO(id,food.getFoodName(),food.getExpDate(),food.getLocation(),food.getCategory(),food.getUserName()));
    }

    /**
     * Method for finding a specific FoodDTO in the list.
     * @param name The name of the item, used to for finding unique objects in unison with expDate.
     * @param expDate The expiration date of the item.
     * @return
     */
    public IFoodDTO readFood(String name, Date expDate) {
        for (int i = 0; i <foodList.size() ; i++) {
            if(foodList.get(i).getFoodName().equals(name) && foodList.get(i).getExpDate().equals(expDate))
                return foodList.get(i);
        }
        return null;
    }

    /**
     * Method for updating an item in the list, where it is set to be a new FoodDTO.
     *@param newFood The new version of the item, which should be set as.
     */
    public void updateFood(IFoodDTO newFood) {


        int pos = -1; //= foodList.indexOf(newFood.getID());

        for (int i = 0; i <foodList.size() ; i++) {
            if (foodList.get(i).equals(newFood.getID()));
            {
                pos = i;
                break;
            }
        }

        foodList.set(pos, newFood);
    }

    /**
     * Method for deleting one food item.
     * @param food the item which is to be deleted.
     */
    public void deleteFood(IFoodDTO food) {
       //foodList.remove(food);
        //foodList.remove(food.getID());

        int index = -1;
        for (int i = 0; i <foodList.size() ; i++) {
            if (foodList.get(i).getID()== food.getID()){
                index = i;
                break;
            }
        }
        foodList.remove(index);
    }

    /**
     * THis methods deletes all entries from the desired location.
     * @param location The location of deletion.
     */
    public void deleteAll(ELocation location, String userName) {

        int size = foodList.size();

        for (int i = size - 1; i >= 0; i--) {
            if ((foodList.get(i).getLocation().equals(location) && foodList.get(i).getUserName().equals(userName))
                    || ((location.equals(ELocation.All)) && foodList.get(i).getUserName().equals(userName))) {
                foodList.remove(i);

            }
        }
    }
    /**
     * For testing purposes atm.
     * @return This list.
     */
    public ArrayList<IFoodDTO> getFoodList() {
        return foodList;
    }

    public IFoodDTO readFood(int foodID) {
        int pos = -1;

        for (int i = 0; i <foodList.size() ; i++) {
            if (foodList.get(i).equals(foodID)) {
                pos = i;
                break;
            }
        }
        return new FoodDTO(foodList.get(pos).getID(), foodList.get(pos).getFoodName(), foodList.get(pos).getExpDate(),foodList.get(pos).getLocation(),foodList.get(pos).getCategory(),foodList.get(pos).getUserName());
    }
}