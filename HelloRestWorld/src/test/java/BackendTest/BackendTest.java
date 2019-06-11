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
    void createConnection() throws SQLException {
        try {

            backend.createConnection();
            backend.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
            fail();


        }
    }

//TODO check dates writes 2019-06-10 in the data-base
    @Test
    void createFood() throws SQLException {
        try {
            backend.createConnection();
            IFoodDTO foo = new FoodDTO("Popsickle", Date.valueOf("2019-06-11"), ELocation.Pantry, ECategory.Vegetable, 1, "Pur");
            int size = backend.getLastID();
            backend.closeConnection();

            int length = 10;
            for (int i = 0; i < length ; i++) {
                backend.createFood(foo);
            }
            size += length;
            backend.createConnection();
            int totalSize = backend.getLastID();
            backend.closeConnection();
            assertEquals(totalSize,size);

        }catch (SQLException e){
            e.printStackTrace();
            fail();

        }
    }
    @Test
    void readFoods() throws SQLException {

    }

    @Test
    void updateFood() throws SQLException {
        IFoodDTO foo = new FoodDTO(3,"smør", Date.valueOf("2019-06-03"), ELocation.Freezer, ECategory.Beef, 5, "Pur");
        backend.updateFood(foo);


    }

    @Test
    void deleteFood() throws SQLException {
        int size = backend.getLastID();
        backend.deleteFood(1);
        backend.createConnection();
        assertEquals(size,(backend.getLastID()-1));
        backend.closeConnection();



    }

    @Test
    void deleteAllFoods() throws SQLException {

        backend.deleteAllFoods(ELocation.Pantry,"Pur");
        backend.createConnection();
        assertEquals(0,backend.getLastID());
        backend.closeConnection();
    }
}
