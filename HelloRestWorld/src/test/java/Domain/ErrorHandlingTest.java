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
    private DatabaseDataLayer bd = new DatabaseDataLayer();
    private ArrayDataLayer dl = new ArrayDataLayer();


    /**
     * Test if we are able to add food from the domain layer
     */
    @Test
    void addFood() {

        try {
            eh.addUser("Test");
            IFoodDTO foo = new FoodDTO(0 ,"Popsickle", Date.valueOf("2019-06-25").getTime(), ELocation.Pantry, ECategory.Vegetable,"Test");
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
        IFoodDTO foo = new FoodDTO(1,"TestFood", Date.valueOf("2019-06-22").getTime(), ELocation.Pantry, ECategory.Vegetable, "Test");
        eh.addUser("Test");
        eh.addFood(foo);
        eh.deleteFood("Test", foo.getID());
        List<IFoodDTO> foodDTOList = eh.getFoodList("Test", ELocation.Pantry.ordinal());
        assertEquals(0, foodDTOList.size());
        eh.deleteUser("Test");
    }

    /**
     * Checks if we can get a list of food.
     * @throws SQLException
     */
    @Test
    void getFoodList() throws SQLException {
        eh.addUser("Test");
        IFoodDTO foo = new FoodDTO(1,"TestFood", Date.valueOf("2019-06-22").getTime(), ELocation.Pantry, ECategory.Vegetable, "Test");
        eh.addFood(foo);
        List<IFoodDTO> list = eh.getFoodList("Test", ELocation.Pantry.ordinal());
        assertEquals(1, list.size());
        eh.deleteUser("Test");

    }

    /**
     * Gets a single food element
     * @throws SQLException
     */
    @Test
    void getFoodItem() throws SQLException {
        eh.addUser("Test");
        IFoodDTO foo = new FoodDTO(1,"TestFood", Date.valueOf("2019-06-22").getTime(), ELocation.Pantry, ECategory.Vegetable, "Test");
        eh.addFood(foo);
        IFoodDTO foodDTO = eh.getFoodItem("Test", 1);
        assertEquals(foo, foodDTO);
        eh.deleteUser("Test");
    }

    /**
     * Checks if any food has an expiration date within X days.
     * @throws SQLException
     */
    @Test
    void getExpiredFoods() throws SQLException {
        eh.addUser("Test");
        IFoodDTO foo = new FoodDTO(1,"TestFood", Date.valueOf("2019-06-22").getTime(), ELocation.Pantry, ECategory.Vegetable, "Test");
        eh.addFood(foo);
        List<IFoodDTO> list = eh.getExpiredFoods("Test", 3);
        assertEquals(1, list.size());
        eh.deleteUser("Test");
    }

    /**
     * Checks if the user exist or not.
     */
    @Test
    void authenticateUser() {
    assertFalse(eh.authenticateUser("TheOneWhoDoesNotExist"));
    }
}