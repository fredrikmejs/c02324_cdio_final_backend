package Domain;

import Backend.*;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;
import jdk.nashorn.internal.ir.IfNode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandling implements IErrorHandling {

    private Backend backend = new Backend();
    private DataLayer dataLayer = new DataLayer();

    public boolean addFood(IFoodDTO food) throws SQLException {

        checkCategory(food);
        checkLocation(food);
        backend.createFood(food);
        dataLayer.addFood(food);

        return true;
    }

    public boolean updateFood(IFoodDTO foodDTO) throws SQLException {

//        checkCategory(oldFood);
//        checkLocation(oldFood);
//        backend.updateFood(newFood);
//        dataLayer.updateFood(oldFood,newFood);
          backend.updateFood(foodDTO);

        return true;
    }

    public boolean deleteFood(IFoodDTO food) throws SQLException {

        checkLocation(food);
        checkCategory(food);

        backend.deleteFood(food.getID(),food.getUserName());
        dataLayer.deleteFood(food);

        return false;
    }

    public boolean deleteAll(ELocation location, String userName, IFoodDTO food) throws SQLException {


        checkLocation(food);
        backend.deleteAllFoods(userName,location);
        dataLayer.deleteAll(location,userName);

        return false;
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

        } catch (Exception e) {
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