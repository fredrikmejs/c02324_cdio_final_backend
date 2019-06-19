package BackendTest;

import Backend.Backend;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.experimental.categories.Categories;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BackendTest {
    private  Backend backend = new Backend();


    @Test
    void createConnection() {
        try {

            backend.createConnection();
            backend.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test
    void createFood()  {
        try {
            backend.createConnection();
            backend.createUser("Test1");
            IFoodDTO foo = new FoodDTO("Popsickle", Date.valueOf("2019-06-25"), ELocation.Pantry, ECategory.Vegetable,"Test1");
            int size = backend.getFoodList(foo.getUserName(), ELocation.Pantry.ordinal()).size();

            int length = 10;
            for (int i = 0; i < length ; i++) {
                backend.createFood(foo);
            }
            size += length;

            int totalSize = backend.getFoodList(foo.getUserName(), ELocation.Pantry.ordinal()).size();
            backend.deleteUser("Test1");
            backend.closeConnection();
            assertEquals(totalSize,size);

        }catch (SQLException e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readFoods() throws SQLException {
        IFoodDTO food = new FoodDTO(1,"Test2", Date.valueOf("2019-06-19"),ELocation.Pantry, ECategory.Beef, "Test2");
        backend.createConnection();
        backend.createUser("Test2");
        backend.createFood(food);
        IFoodDTO iFoodDTO = backend.readFood("Test2", 1);
        assertTrue(food.equals(iFoodDTO));
        backend.deleteUser("Test2");
        backend.closeConnection();
    }

    @Test
    void updateFood() throws SQLException {
        IFoodDTO food = new FoodDTO(1,"Test", new Date(System.currentTimeMillis()),ELocation.Pantry, ECategory.Beef, "Test");
        backend.createConnection();
        backend.createUser("Test");
        backend.createFood(food);

        IFoodDTO foo = new FoodDTO(1,"UpdatedTest", food.getExpDate(),ELocation.Pantry, ECategory.Beef, "Test");
        backend.updateFood(foo);
        food = backend.readFood("Test", 1);
        assertEquals(foo, food);
        backend.deleteUser("Test");
        backend.closeConnection();
    }


    @Test
    void deleteFood() throws SQLException {
        backend.createConnection();
        backend.createUser("Test1");
        IFoodDTO food = new FoodDTO(1,"Test", new Date(System.currentTimeMillis()),ELocation.Pantry, ECategory.Beef, "Test1");
        backend.createFood(food);
        backend.deleteFood(food.getID(), "Test1");
        List<IFoodDTO> list = backend.getFoodList("Test1", ELocation.Pantry.ordinal());
        assertEquals(0, list.size());
        backend.deleteUser("Test1");
        backend.closeConnection();
    }

    @Test
    void deleteAllFoods() throws SQLException {
        backend.createConnection();
        backend.createUser("Test1");
        IFoodDTO foo = new FoodDTO(1,"Test", new Date(System.currentTimeMillis()),ELocation.Pantry, ECategory.Beef, "Test1");
        backend.deleteAllFoods(foo.getUserName(),foo.getLocation());
        List<IFoodDTO> list = backend.getFoodList("Test1", ELocation.Pantry.ordinal());
        assertEquals(0, list.size());
        backend.deleteUser("Test1");
        backend.closeConnection();
    }
}
