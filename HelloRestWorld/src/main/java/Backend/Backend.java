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

    private void createConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185140?"
                + "user=s185140&password=YKl6EOAgNqhvE0fjJ0uJX");
    }
    private void closeConnection() throws SQLException {
        con.close();
    }

    public boolean createFood(IFoodDTO food) throws SQLException {
        createConnection();
        String query = "INSERT INTO Food(food_id, food_name, expirering_date, " +
                "loc_id, cat_id, amount, user_name) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,getLastID()+1);
        psQuery.setString(2, food.getFoodName());
        psQuery.setDate(3, food.getExpDate());
        psQuery.setInt(4, food.getLocation().ordinal());
        psQuery.setInt(5, food.getCategory().ordinal());
        psQuery.setDouble(6,food.getAmount());
        psQuery.setString(7,food.getUserName());
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    public List readFoods(int id) throws SQLException {
        ResultSet rs;

        String query = "SELECT * FROM Food WHERE food_id = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,id);
        rs = psQuery.executeQuery();
        List<IFoodDTO> userList = new ArrayList<>();
        while (rs.next()) {
            int foodId = rs.getInt("food_id");
            String foodName = rs.getString("food_name");
            Date expDate = rs.getDate("expirering_date");
            ELocation location = ELocation.values()[ rs.getInt("location")];
            ECategory category = ECategory.values()[rs.getInt("category")];
            double amount = rs.getDouble("amount");
            String userName = rs.getString("user_name");

            IFoodDTO user = new FoodDTO(foodName, expDate, location, category, amount, userName);
            userList.add(user);
        }
        return userList;
    }

    public boolean updateFood(IFoodDTO food) throws SQLException {
        createConnection();
        String query = "UPDATE Food SET category = ? AND location = ? AND expirering_date = ? " +
                "AND food_name = ? AND amount = ? AND user_name = ?  WHERE food_id = ?";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setInt(1, food.getCategory().ordinal());
        prepStat.setInt(2,food.getLocation().ordinal());
        prepStat.setDate(3,food.getExpDate());
        prepStat.setString(4, food.getFoodName());
        prepStat.setDouble(5,food.getAmount());
        prepStat.setString(6,food.getUserName());
        prepStat.setInt(7, food.getID());

        if (!prepStat.execute()) throw new SQLException();

        con.close();
        return true;
    }

    public boolean deleteFood(int foodId) throws SQLException {
        createConnection();
        String query = "DELETE FROM Food" +
                "WHERE food_id = ? ";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,foodId );
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    public boolean deleteAllFoods(ELocation location, String userName) throws SQLException {
        createConnection();
        String query = "DELETE ALL FROM Food WHERE user_name = ? AND location = ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1,userName);
        psQuery.setObject(2,location);

        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    private int getLastID() throws SQLException {
        int ID = -1;
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
}