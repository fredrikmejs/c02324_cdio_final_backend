package Domain;

import Backend.*;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlingTest {
    private ErrorHandling eh = new ErrorHandling();
    private Backend bd = new Backend();
    private ArrayDataLayer dl = new ArrayDataLayer();


    /**
     * Test if we are able to add food from the domain layer
     */
    @Test
    void addFood() {

        try {
            eh.addUser("Test");
            IFoodDTO foo = new FoodDTO(0 ,"Popsickle", Date.valueOf("2019-06-25"), ELocation.Pantry, ECategory.Vegetable,"Test");
            eh.addFood(foo);
            IFoodDTO foodDTO = eh.getFoodItem("Test", 1);
            assertEquals(foo, foodDTO);
            eh.deleteUser("Test");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if we are able to delete food
     * @throws SQLException
     */
    @Test
    void deleteFood() throws SQLException {
        IFoodDTO foo = new FoodDTO(1,"TestFood", Date.valueOf("2019-06-22"), ELocation.Pantry, ECategory.Vegetable, "Test");
        eh.addUser("Test");
        eh.addFood(foo);
        eh.deleteFood("Test", foo.getID());
        List<IFoodDTO> foodDTOList = eh.getFoodList("Test", ELocation.Pantry.ordinal());
        assertEquals(0, foodDTOList.size());
        eh.deleteUser("Test");
    }

    /**
     * Checks if we can get a list of food. The test print something to the terminal, and the test result should be readen yourself.
     * @throws SQLException
     */
    @Test
    void getFoodList() throws SQLException {
    System.out.println(eh.getFoodList("Pur",ELocation.Pantry.ordinal() ).toString());
    }

    /**
     * Like getFoodList, you get a single food element and the result shall be determent yourself.
     * @throws SQLException
     */
    @Test
    void getFoodItem() throws SQLException {
        System.out.println(eh.getFoodItem("Pur",5).toString());
    }

    /**
     * Checks if any food as an expiration date inside of 3 days and if we are able to update a food.
     * @throws SQLException
     */
    @Test
    void getExpiredFoods() throws SQLException {
        IFoodDTO foo = new FoodDTO(3,"Popsickle", Date.valueOf("2019-06-19"), ELocation.Pantry, ECategory.Vegetable, "Pur");
        eh.updateFood(foo);
        System.out.println(eh.getExpiredFoods("Pur",3));
    }

    /**
     * Checks if the user exist or not.
     */
    @Test
    void authenticateUser() {
    assertFalse(eh.authenticateUser("Per"));
    }
}