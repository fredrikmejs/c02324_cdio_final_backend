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
        backend.createConnection();
        boolean success;
        checkCategory(food);
        checkLocation(food);
        success = backend.createFood(food);
        dataLayer.addFood(food);
        backend.closeConnection();
        if(success){
            return true;
        }else{
            return false;
        }
    }

    public boolean updateFood(IFoodDTO foodDTO) throws SQLException {

        backend.createConnection();
//        checkCategory(oldFood);
//        checkLocation(oldFood);
//        backend.updateFood(newFood);
//        dataLayer.updateFood(oldFood,newFood);

        boolean success =  backend.updateFood(foodDTO);
        backend.closeConnection();
        if(success){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteFood(String userName, int id) throws SQLException {

        backend.createConnection();
//        checkLocation(food);
//        checkCategory(food);
        boolean success =  backend.deleteFood(id,userName);
        backend.closeConnection();
        if(success){
            return true;
        }else{
            return false;
        }
//        dataLayer.deleteFood(food);

    }

    public boolean deleteAll(ELocation location, String userName) throws SQLException {

        backend.createConnection();
//        checkLocation(food);
        boolean success =  backend.deleteAllFoods(userName,location);
        dataLayer.deleteAll(location,userName);
        backend.closeConnection();
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
        backend.createConnection();
        boolean success = true;
        List<IFoodDTO> foodDTOList = new ArrayList<>();
        try {

           foodDTOList = backend.getFoodList(userName);
           backend.closeConnection();
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
        backend.createConnection();
        IFoodDTO  food =  backend.readFood(userName,id);
        backend.closeConnection();
        if(food != null){
            return food;
        }else{
            return null;
        }
    }

    public List<IFoodDTO> getExpiredFoods(String userName, int days) throws SQLException{
//        TODO: Fix Errorhandling and add to interface
        return backend.getExpiredFood(days, userName);
    }

    public boolean addUser(String userName){
        try {
            backend.createConnection();
            backend.createUser(userName);
            backend.closeConnection();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}