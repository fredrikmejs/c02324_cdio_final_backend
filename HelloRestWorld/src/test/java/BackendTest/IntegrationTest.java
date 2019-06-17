package BackendTest;

import Domain.ErrorHandling;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.json.HTTP;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class IntegrationTest {

    @Test
    public void IntegrationTest(){
        ErrorHandling er = new ErrorHandling();
        String userName = "Test";
        //Create user
        boolean userExists = er.authenticateUser(userName);
        if(!userExists){
            try {
                er.addUser(userName);
                assertEquals(true, er.authenticateUser(userName));
            } catch (SQLException e) {
                fail();
                e.printStackTrace();
            }
        }else {
            assertTrue(userExists);
        }

//        Add food to user
        IFoodDTO testFood = new FoodDTO("Salt", Date.valueOf("2019-06-25"), ELocation.Pantry, ECategory.Other,userName);
        try {
            boolean success = er.addFood(testFood);
            assertTrue(success);
        } catch (SQLException e) {
            //fail();
            e.printStackTrace();
        }

        //Get food
        IFoodDTO foodDTO = new FoodDTO();
        try {
            foodDTO = er.getFoodItem(userName, 1);
            assertNotEquals(null, foodDTO);
            assertEquals(testFood, foodDTO);
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Get food list
        List<IFoodDTO> foodDTOList;
        try {
            foodDTOList = er.getFoodList(userName);
            assertEquals(1, foodDTOList.size());
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }
        //Update
        foodDTO.setFoodName("N/A");
        try {
            er.updateFood(foodDTO);
            assertNotEquals(testFood, er.getFoodItem(userName, 1));
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Expiration date
        List<IFoodDTO> expFoods;
        try {
            expFoods = er.getExpiredFoods(userName, 1000);
            assertEquals(1, expFoods.size());
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Delete
        try {
            er.addFood(testFood);
            assertEquals(2, er.getFoodList(userName).size());
            er.deleteFood(userName, 1);
            assertEquals(1, er.getFoodList(userName).size());
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Delete all
        try {
            er.deleteAll(ELocation.Pantry, userName);
            List<IFoodDTO> foodToBeDeleted = er.getFoodList(userName);
            assertEquals(0, foodToBeDeleted.size());
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Delete User
        try {
            userExists = true;
            er.deleteUser(userName);
            userExists = er.authenticateUser(userName);
            assertTrue(userExists);
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

    }
}
