package Backend;

import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class DataLayerTest {

    DataLayer dl = new DataLayer();

    @Test
    void addFood() {
        int preSize = dl.getFoodList().size();
        IFoodDTO food = new FoodDTO("candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer);
        dl.addFood(food);
        assertEquals(preSize+1, dl.getFoodList().size());
    }

    @Test
    void updateFood() {
        IFoodDTO newFood = new FoodDTO("candy", Date.valueOf("2018-09-10"), ECategory.Dairy, ELocation.Fridge);
        dl.addFood(new FoodDTO("candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer));
        dl.updateFood(dl.getFoodList().get(0), newFood);
        assertEquals(newFood, dl.getFoodList().get(0));
    }

    @Test
    void deleteFood() {

        IFoodDTO food = new FoodDTO("candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer);
        dl.addFood(food);
        assertEquals(1,dl.getFoodList().size());
        dl.deleteFood(food);
        assertEquals(0,dl.getFoodList().size());


    }

    @Test
    void deleteAll() {
        IFoodDTO food = new FoodDTO("candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);

        assertEquals(10,dl.getFoodList().size());

        dl.deleteAll(ELocation.All);
        assertEquals(0,dl.getFoodList().size());


    }
}