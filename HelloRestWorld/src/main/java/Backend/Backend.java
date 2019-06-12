package Backend;


import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Backend implements IFoodDAO {

    private static Connection con;

    public void createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        con = DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185140?"
                + "user=s185140&password=YKl6EOAgNqhvE0fjJ0uJX");
    }
    public void closeConnection() throws SQLException {
        con.close();
    }

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

    public boolean updateFood(IFoodDTO food) throws SQLException {

        createConnection();
        String query = "UPDATE Food SET food_name = ?, loc_id = ?," +
                " cat_id = ?,expiration_date = ? WHERE food_id = ? AND user_name = ?";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setString(1,food.getFoodName());
        prepStat.setInt(2,food.getLocation().ordinal());
        prepStat.setInt(3, food.getCategory().ordinal());
        prepStat.setDate(4,food.getExpDate());
        prepStat.setInt(5, food.getID());
        prepStat.setString(6,food.getUserName());
        prepStat.executeUpdate();

        con.close();
        return true;
    }

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

    public boolean deleteAllFoods(String userName, ELocation location) throws SQLException {
        createConnection();
        String query = "DELETE FROM Food WHERE user_name = ? AND loc_id = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1,userName);
        psQuery.setInt(2,location.ordinal());

        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    public int getLastID() throws SQLException {
        createConnection();
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
        closeConnection();
        return ID;
    }


}