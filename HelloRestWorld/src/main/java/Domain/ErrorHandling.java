package Domain;

import Backend.*;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandling implements IErrorHandling {

    private DatabaseDataLayer backend = new DatabaseDataLayer();

    /**
     *Method adds food to the database through the backend
     * @param food The food object to be added.
     * @return true/false on success/failure
     * @throws SQLException
     */
    public boolean addFood(IFoodDTO food) throws SQLException {
        backend.createConnection();
        boolean success;
        checkCategory(food.getCategory());
        checkLocation(food.getLocation());
        success = backend.createFood(food);
        backend.closeConnection();
        return success;
    }

    public boolean updateFood(IFoodDTO foodDTO) throws SQLException {
        Boolean success;
        backend.createConnection();

        if (!checkCategory(foodDTO.getCategory()))
            return false;
        if (!checkLocation(foodDTO.getLocation())){
            return false;
        }



        success =  backend.updateFood(foodDTO);
        backend.closeConnection();
        return success;
    }

    public boolean deleteFood(String userName, int id) throws SQLException {
        boolean success;
        backend.createConnection();

        success =  backend.deleteFood(id,userName);
        backend.closeConnection();
        return success;


    }

    public boolean deleteAll(ELocation location, String userName) throws SQLException {

        backend.createConnection();
        checkLocation(location);
        boolean success =  backend.deleteAllFoods(userName,location);
        backend.closeConnection();
        return success;
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
    public List<IFoodDTO> getFoodList(String userName, int location) throws SQLException{
        backend.createConnection();
        boolean success = true;
        List<IFoodDTO> foodDTOList = new ArrayList<>();
        try {

           foodDTOList = backend.getFoodList(userName, location);
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
        //TODO figure if we want it here?
        //dataLayer.readFood(userName,id);
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


        DatabaseDataLayer backend = new DatabaseDataLayer();

        List<IFoodDTO> foodList = backend.getExpiredFood(days,userName);

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
            boolean success;
            backend.createConnection();
            success = backend.authenticateUser(userName);
            backend.closeConnection();
            return success;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

