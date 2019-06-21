package BackendTest;

import Domain.ErrorHandling;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class IntegrationTest {

    @Test
    void IntegrationTest(){
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
        IFoodDTO testFood = new FoodDTO(1,"Salt", Date.valueOf("2019-06-25").getTime(), ELocation.Pantry, ECategory.Vegetable,userName);
        try {
            boolean success = er.addFood(testFood);
            assertTrue(success);
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Get food
        IFoodDTO foodDTO = new FoodDTO();
        try {
            foodDTO = er.getFoodItem(userName, 1);
            assertTrue(testFood.equals(foodDTO));
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Get food list
        List<IFoodDTO> foodDTOList;
        try {
            foodDTOList = er.getFoodList(userName, ELocation.Pantry.ordinal());
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
            assertEquals(2, er.getFoodList(userName, ELocation.Pantry.ordinal()).size());
            er.deleteFood(userName, 1);
            assertEquals(1, er.getFoodList(userName, ELocation.Pantry.ordinal()).size());
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

        //Delete all
        try {
            er.deleteAll(ELocation.Pantry, userName);
            List<IFoodDTO> foodToBeDeleted = er.getFoodList(userName, ELocation.Pantry.ordinal());
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
            assertFalse(userExists);
        } catch (SQLException e) {
            fail();
            e.printStackTrace();
        }

    }
}
