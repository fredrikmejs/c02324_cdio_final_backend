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
            backend.createUser("Pur");
            IFoodDTO foo = new FoodDTO("Popsickle", Date.valueOf("2019-06-25"), ELocation.Pantry, ECategory.Vegetable,"Pur");
            int size = backend.getLastID(foo.getUserName());

            int length = 10;
            for (int i = 0; i < length ; i++) {
                backend.createFood(foo);
            }
            size += length;

            int totalSize = backend.getLastID(foo.getUserName());
            backend.deleteUser("Pur");
            backend.closeConnection();
            assertEquals(totalSize,size);

        }catch (SQLException e){
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void readFoods() throws SQLException {
        createFood();
        backend.createConnection();
        assertEquals(backend.getLastID("Pur"),backend.getFoodList("Pur", ELocation.Pantry.ordinal()).size());
        if (backend.getFoodList("pur", ELocation.Pantry.ordinal()).isEmpty()){
            System.out.println("The list is empty");
        }else
           System.out.println(backend.getFoodList("Pur", ELocation.Pantry.ordinal()).toString());

        backend.closeConnection();
    }

    @Test
    void updateFood() throws SQLException {
        backend.createConnection();
        IFoodDTO foo = new FoodDTO(3,"smør", Date.valueOf("2019-06-03"), ELocation.Freezer, ECategory.Beef, "Pur");
        backend.updateFood(foo);
        backend.closeConnection();
    }


    @Test
    void deleteFood() throws SQLException {
        backend.createConnection();
        int size = backend.getLastID("Pur")-1;

        backend.deleteFood(10,"Pur");

        int size1 = backend.getLastID("Pur");
        assertEquals(size,size1);

        backend.closeConnection();
    }

    @Test
    void deleteAllFoods() throws SQLException {
        backend.createConnection();
        IFoodDTO foo = new FoodDTO("Popsickle", Date.valueOf("2019-06-11"), ELocation.Pantry, ECategory.Vegetable,"Pur");
        backend.deleteAllFoods(foo.getUserName(),foo.getLocation());
        assertEquals(0,backend.getLastID("Pur"));
        backend.closeConnection();
    }
}
