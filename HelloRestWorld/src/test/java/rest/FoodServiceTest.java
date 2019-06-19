package rest;

import Domain.ErrorHandling;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FoodServiceTest {
    String userName = "Test";
    ELocation location = ELocation.Pantry;
    ECategory category = ECategory.Beef;
    String date = "2019-06-22";
    int foodID = 1;
    FoodService fs = new FoodService();
    ErrorHandling eh = new ErrorHandling();
    IFoodDTO foo = new FoodDTO(foodID,"TestFood", Date.valueOf(date), location, category, userName);

    @BeforeEach
    void setUp() throws SQLException {
        eh.addUser(userName);
        eh.addFood(foo);
    }

    @AfterEach
    void tearDown() throws SQLException{
        eh.deleteUser(userName);
    }

    @Test
    void getAllFoods(){
        try {
            eh.addFood(foo);
            Response response1 = fs.getAllFoods(userName, location.ordinal());
            assertEquals(200, response1.getStatus());
            JsonParser jsonParser = new JsonParser();
            String data = response1.getEntity().toString();
            JsonElement tradeElement = jsonParser.parse(data);
            JsonArray jsonArray = tradeElement.getAsJsonArray();
            assertEquals(2, jsonArray.size());
        }catch(SQLException e){
            fail();
        }
    }

    @Test
    void getFood() {
        Response response1 = fs.getFood(foodID, userName);
        assertEquals(200, response1.getStatus());
    }

    @Test
    void createFood() {
    }

    @Test
    void deleteFood() {
    }

    @Test
    void deleteAllFood() {
    }

    @Test
    void updateFood() {
    }

    @Test
    void getExpiredFoods() {
    }

    @Test
    void verifyUser() {
    }

    @Test
    void addUser() {
    }

    @Test
    void deleteUser() {
    }
}