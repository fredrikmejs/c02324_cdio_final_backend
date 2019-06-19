package Backend;


import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import rest.DTS;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Backend implements IFoodDAO {

    private static Connection con;


    /**
     * Creates an connection to our data base
     * @throws SQLException
     */
    public void createConnection() throws SQLException {
        //Enables TomCat to connect to MySQL database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185140?"
                + "user=s185140&password=YKl6EOAgNqhvE0fjJ0uJX");
    }

    /**
     * Closes the connection to our data base
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        con.close();
    }

    /**
     * Creates a food object to our data base.
     * @param food It's a IFoodDTO
     * @return
     * @throws SQLException
     */
    public boolean createFood(IFoodDTO food)  {
        try {
//            Starts query for insertion
            String query = "INSERT INTO Food(food_id, food_name, " +
                    "loc_id, cat_id, user_name, expiration_date) " +
                    "VALUES(?, ?, ?, ?, ?, ?)";
            PreparedStatement psQuery = con.prepareStatement(query);
//            Sets the parameters of the query to their respective values
            psQuery.setInt(1, getLastID(food.getUserName()) + 1);
            psQuery.setString(2, food.getFoodName());
            psQuery.setInt(3, food.getLocation().ordinal());
            psQuery.setInt(4, food.getCategory().ordinal());
            psQuery.setString(5, food.getUserName());
//            Checks if the food has been placed in the freezer
            if (food.getLocation().ordinal() == 0) {
//                Sets the expiration date for the food in the freezer
                psQuery.setDate(6, getExpirationDate(food.getCategory()));
            }else {
//                Sets the expiration date for the food.
                psQuery.setDate(6, food.getExpDate());
            }
            psQuery.execute();

        } catch (SQLException e) {
//            If it fails, return false
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Get's a list of the food from the data-base
     * @param name finds the food from the username
     * @return Returns a userlist
     * @throws SQLException
     */
    public List<IFoodDTO> getFoodList(String name, int location) throws SQLException {
//        Prepare for SQL statement
        ResultSet rs;
        String query = "SELECT * FROM Food WHERE user_name = ? AND loc_id = ?";
        PreparedStatement foodQuery = con.prepareStatement(query);
        foodQuery.setString(1,name);
        foodQuery.setInt(2, location);
        rs = foodQuery.executeQuery();
//        Storage for food items
        List<IFoodDTO> foodList = new ArrayList<>();
//        While loop to go through all items in the result set
        while (rs.next()) {
            int foodId = rs.getInt("food_id");
            String foodName = rs.getString("food_name");
            Date expDate = rs.getDate("expiration_date");

            ELocation loc_id = ELocation.values()[rs.getInt("loc_id")];
            ECategory category = ECategory.values()[rs.getInt("cat_id")];
            String userName = rs.getString("user_name");
//          Creates a new food from the received parameters to add to the list.
            IFoodDTO food = new FoodDTO(foodId, foodName, expDate, loc_id, category, userName);
            foodList.add(food);
        }
        return foodList;
    }

    /**
     * Update the food from the data base.
     * Checks which objects of the food that we need to update.
     * @param food is a IFoodDTO
     * @return true if successful.
     * @throws SQLException
     */
    public boolean updateFood(IFoodDTO food) {
        try {
            IFoodDTO foodN = readFood(food.getUserName(), food.getID());
//        Determine if any of the modifiable variables have been changed
            if (food.getLocation() != null)
                foodN.setLocation(food.getLocation());
            if (food.getCategory() != null)
                foodN.setCategory(food.getCategory());
            if (food.getExpDate() != null)
                foodN.setExpDate(food.getExpDate());
            if (food.getFoodName() != null) {
                foodN.setFoodName(food.getFoodName());
            }
//        Prepare a SQL query
            String query = "UPDATE Food SET food_name = ?, loc_id = ?," +
                    " cat_id = ?,expiration_date = ? WHERE food_id = ? AND user_name = ?";
            PreparedStatement prepStat = con.prepareStatement(query);
            prepStat.setString(1, foodN.getFoodName());
            prepStat.setInt(2, foodN.getLocation().ordinal());
            prepStat.setInt(3, foodN.getCategory().ordinal());
//        Check the location for the food
            if (foodN.getLocation().ordinal() == 0){
                prepStat.setDate(4, getExpirationDate(foodN.getCategory()));
            }else{
                prepStat.setDate(4, foodN.getExpDate());
            }
        prepStat.setInt(5, foodN.getID());
        prepStat.setString(6,foodN.getUserName());
//        Food is updated
        prepStat.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
//            returned false if failed
            return false;
        }
        return true;
    }

    /**
     * Deletes one food from the data base
     * @param foodId the id of the food
     * @param userName the user name of the user.
     * @return returns true if successful
     */
    public boolean deleteFood(int foodId, String userName) {
        try {
//        Prepare a SQL query
        String query =" DELETE FROM Food WHERE food_id = ? AND user_name = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,foodId );
        psQuery.setString(2,userName);
//        Execute the query
        psQuery.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deletes all food for a special location for a user.
     * @param userName user name of the user
     * @param location the location of where the food is stored
     * @return returns true if successful
     * @throws SQLException
     */
    public boolean deleteAllFoods(String userName, ELocation location) throws SQLException {

        boolean success;
//            Remove food from the supplied location
            String query = "DELETE FROM Food WHERE user_name = ? AND loc_id = ?";
            PreparedStatement psQuery = con.prepareStatement(query);
            psQuery.setString(1, userName);
            psQuery.setInt(2, location.ordinal());
            success = psQuery.execute();


        return success;
    }

    /**
     * Gives the last ID from the data base, that way we make sure to generate an ID + no duplicates.
     * @return the last ID used in the data base.
     * @throws SQLException
     */
    public int getLastID(String userName) throws SQLException {
        int ID;
//        Gets all food_id belonging to the user
        String query = "SELECT food_id " +
                "FROM Food WHERE user_name = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1,userName);
        ResultSet rs = psQuery.executeQuery();
//        Goes to the bottom of the list and sets the ID to the last used ID + 1
        rs.last();
        try {
            ID = rs.getInt("food_id");
        }catch (SQLException e){
//            If the list is empty, ID is set to 0
            ID = 0;
        }
        return ID;
    }

    /**
     * finds a single food.
     * @param userName the user name of the user.
     * @param foodID the id of the food you want to find.
     * @return return the IFoodDTO of the specific food.
     * @throws SQLException
     */
    public IFoodDTO readFood(String userName, int foodID) throws SQLException{
//        Prepare a SQL query
        String query = "SELECT * from Food where user_name = ? AND food_id = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1, userName);
        psQuery.setInt(2,foodID);
        ResultSet rs = psQuery.executeQuery();
//        Initialise variables
        int foodId = 0;
        String foodName = "";
        Date expDate = new Date(System.currentTimeMillis());
        ELocation location = ELocation.Freezer;
        ECategory category = ECategory.Beef;
        String user_Name = "";
//        Get the first item in the list (list only contains one, at index 0. List starts by default at index -1)
        while(rs.next()) {
            foodId = rs.getInt("food_id");
            foodName = rs.getString("food_name");
            expDate = rs.getDate("expiration_date");

            location = ELocation.values()[rs.getInt("loc_id")];
            category = ECategory.values()[rs.getInt("cat_id")];
            user_Name = rs.getString("user_name");
        }
//        return a new food object from the supplied data
        return new FoodDTO(foodId, foodName, expDate, location, category, user_Name);
    }

    /**
     * Method creates a user in the MySQL database.
     * @param userName This is the username for the user to be created.
     * @return returns true if success, else false.
     */
    public boolean createUser(String userName) {
        try{
        String query = "INSERT INTO User(user_name) VALUES(?)";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1,userName);
        psQuery.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Method checks to see if a user exists in the database
     * @param userName username to check.
     * @return true/false on a success/failure.
     * @throws SQLException
     */
    public boolean authenticateUser(String userName)throws SQLException{
        String query = "SELECT * FROM User WHERE user_name = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();

        String user_name = null;
        while (resultSet.next()){
            user_name = resultSet.getString("user_name");
        }
        return user_name != null;
    }

    /**
     * Method deletes a user from the databse.
     * @param userName Is the username of the user to be deleted.
     * @return true/false on a success/failure.
     * @throws SQLException
     */
    public boolean deleteUser(String userName) throws SQLException{
        String query = "DELETE FROM User WHERE user_name = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, userName);
        boolean success;
        success = preparedStatement.execute();
        return success;
    }

    /**
     * Method to gather a list of foods that will expire in a given amount of days.
     * @param days Number of days before expiration date.
     * @param userName Username of the owner
     * @return returns a List<IFoodDTO> with all expired foods.
     * @throws SQLException
     */
    public List<IFoodDTO> getExpiredFood(int days, String userName) throws SQLException {
        List<IFoodDTO> expiredFoods = new ArrayList<>();

        createConnection();
//        Prepare a SQL query
        String query = "SELECT * " +
                "FROM Food " +
                "WHERE expiration_date <= adddate(current_date(), ?) AND user_name = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1, days);
        psQuery.setString(2, userName);
        ResultSet resultSet = psQuery.executeQuery();
//        Go through the result set and create foods from the data
        while (resultSet.next()) {
            IFoodDTO foodDTO = new FoodDTO(resultSet.getInt("food_id"), resultSet.getString("food_name"),
                    resultSet.getDate("expiration_date"), ELocation.values()[resultSet.getInt("loc_id")],
                    ECategory.values()[resultSet.getInt("cat_id")], resultSet.getString("user_name"));
//            Add the food to the list of expired foods
            expiredFoods.add(foodDTO);
        }
        closeConnection();
        return expiredFoods;
    }

    /**
     * Method gets the expiration date of an item depending on the category it belongs to.
     * @param val This is the category the food item belongs to.
     * @return Returns the date of which the food expires on.
     * @throws SQLException
     */
    private Date getExpirationDate(ECategory val) throws SQLException{
//        Query to access freezer dates
        String query = "select freezer_exp from Freezer_expiration where cat_id =?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,val.ordinal());
        ResultSet rs = psQuery.executeQuery();
        int days = 0;
        while(rs.next())
            days = rs.getInt("freezer_exp");
//        Method to add extra days to the supplied expiration date.
        return DTS.addDays(new Date(System.currentTimeMillis()), days);
    }
}