package Backend;


import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Backend implements IFoodDAO {

    private static String DB_PORT = "3306";
    private static String DB_URI = "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com:"+DB_PORT+"/";
    private static String DB_USER = "s164863";
    private static String DB_PASS = "LPUj5vpaQZepYQgtCtdWR";
    private static String DB_NAME = "s164863";

    private static Connection con;

    public void createConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        try {
            String jdbcUrl = "jdbc:mysql://" + DB_URI + ":" + DB_PORT + "/" + DB_NAME + "?user=" + DB_USER + "&password=" + DB_PASS;

            con = DriverManager.getConnection(
                    DB_URI.concat(DB_NAME), DB_USER, DB_PASS);
        } catch (Exception e) {
            System.out.println("FEJL!" + e.toString());
        }
    }
    public void closeConnection() throws SQLException {
        con.close();
    }

    public boolean createFood(IFoodDTO food) throws SQLException {
        createConnection();

//        Connection con = DriverManager.getConnection(
//                DB_URI, DB_USER, DB_PASS);

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

    public List<IFoodDTO> readFoods() throws SQLException {
        createConnection();
        ResultSet rs;
        Statement queryUser = con.createStatement();
        rs = queryUser.executeQuery(
                "SELECT * FROM Food");
        closeConnection();
        List<IFoodDTO> foodlist = new ArrayList<>();

        while (rs.next()) {
            int foodId = rs.getInt("food_id");
            String foodName = rs.getString("food_name");
            Date expDate = rs.getDate("expirering_date");
            ELocation location = ELocation.valueOf(rs.getString("loc_id"));
            ECategory category = ECategory.valueOf(rs.getString("cat_id"));
            int amount = rs.getInt("amount");
            String userName = rs.getString("user_name");

            IFoodDTO food = new FoodDTO(foodId, foodName, expDate, location, category, amount, userName);

            foodlist.add(food);
        }
        return foodlist;
    }

    public boolean updateFood(IFoodDTO food) throws SQLException {


       // String query1 = "UPDATE s185140.Food SET `loc_id` = '1', `cat_id` = '0', `amount` = '5' WHERE (`food_id` = '3') and (`user_name` = 'Pur');";
       // PreparedStatement pre = con.prepareStatement(query1);

        createConnection();
        String query = "UPDATE Food SET food_name = ?, expirering_date = ?, loc_id = ?," +
                " cat_id = ?, amount = ? WHERE food_id = ? AND user_name = ?;";
        PreparedStatement prepStat = con.prepareStatement(query);
        prepStat.setString(1,food.getFoodName());
        prepStat.setDate(2,food.getExpDate());
        prepStat.setInt(3,food.getLocation().ordinal());
        prepStat.setInt(4, food.getCategory().ordinal());
        prepStat.setDouble(5,food.getAmount());
        prepStat.setInt(6, food.getID());
        prepStat.setString(7,food.getUserName());


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

    //TODO make it work with location
    public boolean deleteAllFoods(ELocation location, String userName) throws SQLException {
        createConnection();
        String query = "DELETE FROM Food WHERE user_name = ?"; //AND loc_id= ?";
        PreparedStatement psQuery = con.prepareStatement(query);
        psQuery.setString(1,userName);
       // psQuery.setObject(2,location);

        boolean success = psQuery.execute();
        closeConnection();
        return success;
    }

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
}