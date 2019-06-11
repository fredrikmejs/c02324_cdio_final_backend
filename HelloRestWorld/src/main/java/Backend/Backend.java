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
        //TODO change to the correct database
        con = DriverManager.getConnection("jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185140?"
                + "user=s185140&password=YKl6EOAgNqhvE0fjJ0uJX");
    }
    public void closeConnection() throws SQLException {
        con.close();
    }


    public boolean createFood(IFoodDTO food) throws SQLException {
        createConnection();
        // TODO maybe change names.
        String query = "INSERT INTO food(food_id,food_name, expirering_date,   " +
                "category, location) " +
                "VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,getLastID()+1);
        psQuery.setString(2, food.getName());
        psQuery.setDate(3, food.getExpDate());
        psQuery.setObject(4, food.getCategory());
        psQuery.setObject(5, food.getLocation());
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    //TODO
    public List readFoods(String name) throws SQLException {
        ResultSet rs;
        Statement queryUser = con.createStatement();
        rs = queryUser.executeQuery(
                "SELECT * FROM user");

        List<IFoodDTO> userList = new ArrayList<>();
        while (rs.next()) {
            int foodId = rs.getInt("food_id");
            String foodName = rs.getString("food_name");
            Date expDate = rs.getDate("expirering_date");
            ELocation location = ELocation.values()[ rs.getInt("location")];
            ECategory category = ECategory.values()[rs.getInt("category")];

            IFoodDTO user = new FoodDTO(foodId,foodName, expDate, category, location);
            userList.add(user);
        }
        return userList;
    }

    public boolean updateFood(IFoodDTO food) throws SQLException {
        //TODO should we be able to update the expiring date?
        createConnection();
        String query = "UPDATE food SET category = ? AND location = ? AND expirering_date = ? " +
                "AND food_name = ? WHERE food_id = ?";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setObject(1, food.getCategory());
        prepStat.setObject(2,food.getLocation());
        prepStat.setDate(3,food.getExpDate());
        prepStat.setString(4, food.getName());
        prepStat.setInt(5, food.getID());
        if (!prepStat.execute()) throw new SQLException();

        con.close();
        return true;
    }

    public boolean deleteFood(int foodId) throws SQLException {
        createConnection();
        String query = "DELETE FROM food" +
                "WHERE food_id = ? ";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setInt(1,foodId );
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    //TODO
    public boolean deleteAllFoods(ELocation location, String userName) throws SQLException {

        createConnection();
        String query = "DELETE ALL WHERE user_name = ? AND location = ?";
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
                "FROM food";
        PreparedStatement psQuery = con.prepareStatement(query);
        ResultSet rs = psQuery.executeQuery();
        rs.last();
        try {
            ID = rs.getInt("id_user");
        }catch (SQLException e){
            ID = 0;
        }
        return ID;
    }

}