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

    /**
     * Testing if we are able to add food to our arraylist.
     */
    @Test
    void addFood() {
        int preSize = dl.getFoodList().size();
        IFoodDTO food = new FoodDTO(2,"candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer);
        dl.addFood(food);
        dl.addFood(food);
        assertEquals(preSize+2, dl.getFoodList().size());
    }

    /**
     * Testing if we are able to update a food
     */
    @Test
    void updateFood() {
        IFoodDTO newFood = new FoodDTO(2,"candy", Date.valueOf("2018-09-10"), ECategory.Dairy, ELocation.Fridge);
        dl.addFood(new FoodDTO(2,"candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer));
        dl.updateFood(dl.getFoodList().get(0), newFood);
        assertEquals(newFood, dl.getFoodList().get(0));
    }

    @Test
    void deleteFood() {
        IFoodDTO food = new FoodDTO(2,"candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer);
        dl.addFood(food);
        assertEquals(1,dl.getFoodList().size());
        dl.deleteFood(food);
        assertEquals(0,dl.getFoodList().size());
    }

    /**
     * Test if we are able to delete the food of the different locations.
     */
    @Test
    void deleteAll() {
        //test if it's delete all the elements.
        IFoodDTO food = new FoodDTO(2,"candy", Date.valueOf("2016-04-07"), ECategory.Beef, ELocation.Freezer);
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

        //Checks if the deletes the elements in the Freezer
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

        dl.deleteAll(ELocation.Freezer);
        assertEquals(0,dl.getFoodList().size());

        // checks if it's deletes elements in the Fridge, with a wrong location given.
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
        dl.deleteAll(ELocation.Fridge);
        assertEquals(10,dl.getFoodList().size());
    }
}