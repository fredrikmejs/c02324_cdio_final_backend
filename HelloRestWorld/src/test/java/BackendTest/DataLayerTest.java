package BackendTest;

import Backend.DataLayer;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataLayerTest {

    private DataLayer dl = new DataLayer();

    /**
     * Testing if we are able to add food to our arraylist.
     */
    @Test
    void addFood() {
        int preSize = dl.getFoodList().size();
        IFoodDTO food = new FoodDTO("Fredrik", Date.valueOf("2018-09-10"),ELocation.Fridge, ECategory.Beef, 50.0,"FredrikMejs");
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        IFoodDTO foodDelete = new FoodDTO(4,"Fredrik", Date.valueOf("2018-09-10"),ELocation.Fridge, ECategory.Beef, 50.0,"FredrikMejs");
        dl.deleteFood(foodDelete);
        foodDelete = new FoodDTO(3,"Fredrik", Date.valueOf("2018-09-10"),ELocation.Fridge, ECategory.Beef, 50.0,"FredrikMejs");
        dl.deleteFood(foodDelete);
        dl.addFood(food);
        dl.addFood(food);

        assertEquals(4, dl.getFoodList().size());

    }

    @Test
    void getFoodList() {
        IFoodDTO food = new FoodDTO("Fredrik", Date.valueOf("2018-09-10"),ELocation.Fridge, ECategory.Beef, 50.0,"FredrikMejs");
        dl.addFood(food);
        IFoodDTO food1 = new FoodDTO("Per",Date.valueOf("2020-09-10"),ELocation.Freezer,ECategory.Fish,30,"GusserNusser");
        dl.addFood(food1);
        System.out.println(dl.getFoodList().toString());



    }


    /**
     * Testing if we are able to update a food
     */
    @Test
    void updateFood() {
        IFoodDTO newFood = new FoodDTO("Fredrik", Date.valueOf("2018-09-10"),ELocation.Fridge, ECategory.Beef, 50.0,"FredrikMejs");
        dl.addFood(new FoodDTO("Fredrik", Date.valueOf("2018-09-10"),ELocation.Freezer, ECategory.Beef, 50.0,"FredrikMejs"));
        dl.updateFood(dl.getFoodList().get(0), newFood);
        assertEquals(newFood, dl.getFoodList().get(0));
    }

    @Test
    void deleteFood() {
        IFoodDTO food = new FoodDTO(0,"Pære", Date.valueOf("2018-09-10"),ELocation.Fridge, ECategory.Beef, 50.0,"FredrikMejs");
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
        IFoodDTO food = new FoodDTO("Fredrik", Date.valueOf("2018-09-10"),ELocation.Freezer, ECategory.Beef, 50.0,"FredrikMejs");
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