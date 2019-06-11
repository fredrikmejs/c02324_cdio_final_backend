package BackendTest;

import Backend.Backend;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
 //TODO when the DB is up
class BackendTest {
    Backend backend = new Backend();

    @Test
    void createFood() {
        try {
            IFoodDTO foo = new FoodDTO("Popsickle", Date.valueOf("2019-06-11"), ELocation.Pantry, ECategory.Vegetable, 1, "Pur");
            assertTrue(backend.createFood(foo));


        }catch (SQLException e){
            e.printStackTrace();
            fail();

        }
    }
    @Test
    void readFoods() {
    }

    @Test
    void updateFood() {
    }

    @Test
    void deleteFood() {
    }

    @Test
    void deleteAllFoods() {
    }
}
