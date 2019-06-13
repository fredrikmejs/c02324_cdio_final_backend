package Backend;


import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import com.google.gson.JsonObject;

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
    public boolean createFood(IFoodDTO food) throws SQLException {
        createConnection();
        String query = "INSERT INTO Food(food_id, food_name, " +
                "loc_id, cat_id, user_name, expiration_date) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,getLastID()+1);
        psQuery.setString(2, food.getFoodName());
        psQuery.setInt(3, food.getLocation().ordinal());
        psQuery.setInt(4, food.getCategory().ordinal());
        psQuery.setString(5,food.getUserName());
        psQuery.setDate(6, food.getExpDate());
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    /**
     * Get's a list of the food from the data-base
     * @param name finds the food from the username
     * @return Returns a userlist
     * @throws SQLException
     */
    public List<IFoodDTO> getFoodList(String name) throws SQLException {
        ResultSet rs;
        createConnection();
        String query = "SELECT * FROM Food WHERE user_name = ?";
        PreparedStatement foodQuery = con.prepareStatement(query);
        foodQuery.setString(1,name);
        rs = foodQuery.executeQuery();
        List<IFoodDTO> foodList = new ArrayList<>();
        while (rs.next()) {
            int foodId = rs.getInt("food_id");
            String foodName = rs.getString("food_name");
            Date expDate = rs.getDate("expiration_date");

            ELocation location = ELocation.values()[rs.getInt("loc_id")];
            ECategory category = ECategory.values()[rs.getInt("cat_id")];
            String userName = rs.getString("user_name");

            IFoodDTO food = new FoodDTO(foodId, foodName, expDate, location, category, userName);
            foodList.add(food);
        }
        closeConnection();
        return foodList;
    }

    /**
     * Update the food from the data base.
     * Checks which objects of the food that we need to update.
     * @param food is a IFoodDTO
     * @return true if successful.
     * @throws SQLException
     */
    public boolean updateFood(IFoodDTO food) throws SQLException {
        IFoodDTO foodN = readFood(food.getUserName(), food.getID());
        if (food.getLocation() != null)
            foodN.setLocation(food.getLocation());
        if (food.getCategory() != null)
            foodN.setCategory(food.getCategory());
        if (food.getExpDate() != null)
            foodN.setExpDate(food.getExpDate());
        if (food.getFoodName() != null)
            foodN.setFoodName(food.getFoodName());
        createConnection();
        String query = "UPDATE Food SET food_name = ?, loc_id = ?," +
                " cat_id = ?,expiration_date = ? WHERE food_id = ? AND user_name = ?";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setString(1,foodN.getFoodName());
        prepStat.setInt(2,foodN.getLocation().ordinal());
        prepStat.setInt(3, foodN.getCategory().ordinal());
        prepStat.setDate(4,foodN.getExpDate());
        prepStat.setInt(5, foodN.getID());
        prepStat.setString(6,foodN.getUserName());
        prepStat.executeUpdate();

        con.close();
        return true;
    }

    /**
     * Deletes one food from the data base
     * @param foodId the id of the food
     * @param userName the user name of the user.
     * @return returns true if successful
     * @throws SQLException
     */
    public boolean deleteFood(int foodId, String userName) throws SQLException {
        createConnection();
        String query =" DELETE FROM Food WHERE food_id = ? AND user_name = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,foodId );
        psQuery.setString(2,userName);
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    /**
     * Deletes all food for a special location for a user.
     * @param userName user name of the user
     * @param location the location of where the food is stored
     * @return returns true if successful
     * @throws SQLException
     */
    public boolean deleteAllFoods(String userName, ELocation location) throws SQLException {
        createConnection();

        boolean success;
        if (ELocation.All == location){
            String query = "DELETE FROM Food WHERE user_name = ?";
            PreparedStatement psQuery = con.prepareStatement(query);
            psQuery.setString(1,userName);
            success = psQuery.execute();

        }else {
            String query = "DELETE FROM Food WHERE user_name = ? AND loc_id = ?";
            PreparedStatement psQuery = con.prepareStatement(query);
            psQuery.setString(1, userName);
            psQuery.setInt(2, location.ordinal());
            success = psQuery.execute();
        }

        closeConnection();
        return success;
    }

    /**
     * Gives the last ID from the data base, that way we make sure to generate an ID + no duplicates.
     * @return the last ID used in the data base.
     * @throws SQLException
     */
    public int getLastID() throws SQLException {
        int ID;
        String query = "SELECT food_id " +
                "FROM Food";
        PreparedStatement psQuery = con.prepareStatement(query);
        ResultSet rs = psQuery.executeQuery();
        rs.last();
        try {
            ID = rs.getInt("food_id");
        }catch (SQLException e){
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
        createConnection();
        String query = "SELECT * from Food where user_name = ? AND food_id = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1, userName);
        psQuery.setInt(2,foodID);
        ResultSet rs = psQuery.executeQuery();
        int foodId = 0;
        String foodName = "";
        Date expDate = new Date(System.currentTimeMillis());
        ELocation location = ELocation.All;
        ECategory category = ECategory.Beef;
        String user_Name = "";
        while(rs.next()) {
            foodId = rs.getInt("food_id");
            foodName = rs.getString("food_name");
            expDate = rs.getDate("expiration_date");

            location = ELocation.values()[rs.getInt("loc_id")];
            category = ECategory.values()[rs.getInt("cat_id")];
            user_Name = rs.getString("user_name");
        }
        closeConnection();
        return new FoodDTO(foodId, foodName, expDate, location, category, user_Name);
    }

    public boolean createUser(String userName) throws SQLException{
        createConnection();
        String query = "INSERT INTO User VALUES(?)";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1,userName);
        psQuery.executeQuery();
        closeConnection();
        return psQuery.execute();
    }

    public List<IFoodDTO> getExpiredFood(int days, String userName) throws SQLException{
        List<IFoodDTO> expiredFoods = new ArrayList<>();

        createConnection();
        String query = "SELECT * " +
                "FROM Food " +
                "WHERE expiration_date <= adddate(current_date(), ?) AND user_name = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1, days);
        psQuery.setString(2, userName);
        ResultSet resultSet = psQuery.executeQuery();
        while(resultSet.next()){
            IFoodDTO foodDTO = new FoodDTO(resultSet.getInt("food_id"),resultSet.getString("food_name"),
                    resultSet.getDate("expiration_date"),ELocation.values()[resultSet.getInt("loc_id")],
                    ECategory.values()[resultSet.getInt("cat_id")],resultSet.getString("user_name"));
            expiredFoods.add(foodDTO);
        }
        closeConnection();
        return expiredFoods;
    }
}