package Domain;

import Backend.*;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandling implements IErrorHandling {

    private Backend backend = new Backend();
    private ArrayDataLayer dataLayer = new ArrayDataLayer();

    public boolean addFood(IFoodDTO food) throws SQLException {
        boolean success;
        checkCategory(food);
        checkLocation(food);
        success = backend.createFood(food);
        dataLayer.addFood(food);
        if(success){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateFood(IFoodDTO foodDTO) throws SQLException {

//        checkCategory(oldFood);
//        checkLocation(oldFood);
//        backend.updateFood(newFood);
//        dataLayer.updateFood(oldFood,newFood);
        boolean success =  backend.updateFood(foodDTO);
        if(success){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteFood(String userName, int id) throws SQLException {

//        checkLocation(food);
//        checkCategory(food);
        boolean success =  backend.deleteFood(id,userName);
        if(success){
            return true;
        }else{
            return false;
        }
//        dataLayer.deleteFood(food);

    }

    public boolean deleteAll(ELocation location, String userName) throws SQLException {


//        checkLocation(food);
        boolean success =  backend.deleteAllFoods(userName,location);
        dataLayer.deleteAll(location,userName);
        if(success){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkCategory(IFoodDTO food) {
        for (ECategory c: ECategory.values()) {
            if (c.equals(food.getCategory()))
                return true;
        }
        return false;
    }

    private boolean checkLocation(IFoodDTO food) {
        for (ELocation l: ELocation.values()) {
            if (l.equals(food.getLocation())) {
                return true;
            }
        }
        return false;
    }
    public List<IFoodDTO> getFoodList(String userName) throws SQLException{
        boolean success = true;
        List<IFoodDTO> foodDTOList = new ArrayList<>();
        try {

           foodDTOList = backend.getFoodList(userName);

        } catch (SQLException e) {
            success = false;
        }
        if(success){
            return foodDTOList;
        }else{
            return null;
        }
    }

    public IFoodDTO getFoodItem(String userName, int id) throws SQLException {

        IFoodDTO  food =  backend.readFood(userName,id);
        if(food != null){
            return food;
        }else{
            return null;
        }
    }
}