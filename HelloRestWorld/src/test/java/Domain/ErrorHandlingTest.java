package Domain;

import Backend.Backend;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ErrorHandlingTest {
    private ErrorHandling eh = new ErrorHandling();
    private Backend bd = new Backend();


    /**
     * Test if we are able to add food from the domain layer
     * @throws SQLException
     */
    @Test
    void addFood() throws SQLException {

        try {
            //eh.addUser("Pur");
            IFoodDTO foo = new FoodDTO("Popsickle", Date.valueOf("2019-06-22"), ELocation.Pantry, ECategory.Vegetable, "Pur");
            bd.createConnection();
            int size = bd.getLastID();
            bd.closeConnection();
            int length = 10;
            for (int i = 0; i < length; i++) {
                eh.addFood(foo);
            }
            size += length;
            bd.createConnection();
            int totalSize = bd.getLastID();
            bd.closeConnection();
            assertEquals(totalSize, size);

        } catch (
                SQLException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Checks if we are able to delete food
     * @throws SQLException
     */
    @Test
    void deleteFood() throws SQLException {
        bd.createConnection();
        int size = bd.getLastID();
        bd.closeConnection();
        eh.deleteFood("Pur",size);
        bd.createConnection();
        assertEquals(size-1,bd.getLastID());
        bd.closeConnection();
    }

    /**
     * Checks if we can get a list of food. The test print something to the terminal, and the test result should be readen yourself.
     * @throws SQLException
     */
    @Test
    void getFoodList() throws SQLException {
    System.out.println(eh.getFoodList("Pur").toString());
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