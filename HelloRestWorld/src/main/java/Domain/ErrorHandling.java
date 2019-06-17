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
        checkCategory(food.getCategory());
        checkLocation(food.getLocation());
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
        Boolean success;
        backend.createConnection();

        if (!checkCategory(foodDTO.getCategory()))
            return false;
        if (!checkLocation(foodDTO.getLocation())){
            return false;
        }

        dataLayer.updateFood(foodDTO);

        success =  backend.updateFood(foodDTO);
        backend.closeConnection();
        if(success){
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteFood(String userName, int id) throws SQLException {
        boolean success;
        backend.createConnection();

        dataLayer.deleteFood(userName,id);
        success =  backend.deleteFood(id,userName);
        backend.closeConnection();
        if(success){
            return true;
        }else{
            return false;
        }


    }

    public boolean deleteAll(ELocation location, String userName) throws SQLException {

        backend.createConnection();
        checkLocation(location);
        boolean success =  backend.deleteAllFoods(userName,location);
        dataLayer.deleteAll(location,userName);
        backend.closeConnection();
        if(success){
            return true;
        }else{
            return false;
        }
    }

    private boolean checkCategory(ECategory category) {
        for (ECategory c: ECategory.values()) {
            if (c.equals(category))
                return true;
        }
        return false;
    }

    private boolean checkLocation(ELocation location) {
        for (ELocation l: ELocation.values()) {
            if (l.equals(location)) {
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


        Backend backend = new Backend();

        List<IFoodDTO> foodList = backend.getExpiredFood(days,userName);

        if (foodList.isEmpty()){
            return null;
        }else
            return foodList;
    }

    /**
     * Method is used to add a user to the database.
     * @param userName Name of the user to be added.
     * @return true on a success, else false.
     */
    public boolean addUser(String userName)throws SQLException{
        boolean success;
            backend.createConnection();
            success = backend.createUser(userName);
            backend.closeConnection();
            return success;

    }

    /**
     * Method is used to delete a user from the database.
     * @param userName Name of the user to be deleted.
     * @return true on a success, else false.
     */
    public boolean deleteUser(String userName)throws SQLException{
            backend.createConnection();
            boolean success = backend.deleteUser(userName);
            backend.closeConnection();
            return success;
    }

    /**
     * Method checks if user exists in database.
     * @param userName Name of user to be checked.
     * @return true on a success, else false.
     */
    public boolean authenticateUser(String userName){
        try{
            backend.createConnection();
            backend.authenticateUser(userName);
            backend.closeConnection();
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

