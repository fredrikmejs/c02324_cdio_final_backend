package Backend;

import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.IFoodDTO;
import java.sql.*;
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
        String query = "INSERT INTO food(food_name, expiring_date,   " +
                "category, location) " +
                "VALUES(?, ?, ?, ?, ?)";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1, food.getName());
        psQuery.setDate(2, food.getExpDate());
        psQuery.setObject(3, food.getCategory());
        psQuery.setObject(4, food.getLocation());
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    //TODO
    public List readFoods(String name) throws SQLException {
        return null;

    }

    public boolean updateFood(IFoodDTO food) throws SQLException {
        //TODO should we be able to update the expiring date?
        createConnection();
        String query = "UPDATE food SET category = ? AND location = ? WHERE food_name = ? AND expiring_date = ? ";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setObject(1, food.getCategory());
        prepStat.setObject(2,food.getLocation());
        prepStat.setString(3, food.getName());
        prepStat.setDate(4, food.getExpDate());
        if (!prepStat.execute()) throw new SQLException();

        con.close();
        return true;
    }

    public boolean deleteFood(String name, Date expDate) throws SQLException {
        createConnection();
        String query = "DELETE FROM food" +
                "WHERE food_name = ? and expiring_date = ? ";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1, name);
        psQuery.setDate(2,expDate);
        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

    //TODO
    public boolean deleteAllFoods(ELocation location) {
        return false;
    }
}
