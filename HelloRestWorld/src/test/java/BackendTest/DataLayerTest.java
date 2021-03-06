package BackendTest;

import Backend.ArrayDataLayer;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class DataLayerTest {

    private ArrayDataLayer dl = new ArrayDataLayer();

    /**
     * Testing if we are able to add food to our arraylist.
     */
    @Test
    void addFood() {
        IFoodDTO food = new FoodDTO("kage", Date.valueOf("2018-09-10").getTime(),ELocation.Fridge, ECategory.Beef, "FredrikMejs");
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        dl.addFood(food);
        IFoodDTO foodDelete = new FoodDTO(4,"kage", Date.valueOf("2018-09-10").getTime(),ELocation.Fridge, ECategory.Beef, "FredrikMejs");
        dl.deleteFood(foodDelete.getUserName(),foodDelete.getID());
        foodDelete = new FoodDTO(3,"kage", Date.valueOf("2018-09-10").getTime(),ELocation.Fridge, ECategory.Beef,"FredrikMejs");
        dl.deleteFood(foodDelete.getUserName(),foodDelete.getID());
        dl.addFood(food);
        dl.addFood(food);

        assertEquals(4, dl.getFoodList().size());

    }

    @Test
    void getFoodList() {
        IFoodDTO food = new FoodDTO("kage", Date.valueOf("2018-09-10").getTime(),ELocation.Fridge, ECategory.Beef,"FredrikMejs");
        dl.addFood(food);
        IFoodDTO food1 = new FoodDTO("kage",Date.valueOf("2020-09-10").getTime(),ELocation.Freezer,ECategory.Fish,"GusserNusser");
        dl.addFood(food1);
        System.out.println(dl.getFoodList().toString());
    }

    /**
     * Testing if we are able to update a food
     */
    @Test
    void updateFood() {
        IFoodDTO newFood = new FoodDTO(1,"kage", Date.valueOf("2018-09-10").getTime(),ELocation.Fridge, ECategory.Beef, "FredrikMejs");
        dl.addFood(new FoodDTO(1,"kage", Date.valueOf("2018-09-10").getTime(),ELocation.Freezer, ECategory.Beef, "FredrikMejs"));
        dl.updateFood(newFood);
        System.out.println(dl.getFoodList().toString());

        assertEquals(newFood, dl.getFoodList().get(0));
    }

    @Test
    void deleteFood() {
        IFoodDTO food = new FoodDTO(1,"Pære", Date.valueOf("2018-09-10").getTime(),ELocation.Fridge, ECategory.Beef, "FredrikMejs");
        dl.addFood(food);
        assertEquals(1,dl.getFoodList().size());
        dl.deleteFood(food.getUserName(),food.getID());
        assertEquals(0,dl.getFoodList().size());
    }

    /**
     * Test if we are able to delete the food of the different locations.
     */
    @Test
    void deleteAll() {
        IFoodDTO food = new FoodDTO("kage", Date.valueOf("2018-09-10").getTime(),ELocation.Freezer, ECategory.Beef, "FredrikMejs");

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


        dl.deleteAll(ELocation.Freezer,"FredrikMejs");
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
        dl.deleteAll(ELocation.Fridge,"FredrikMejs");
        assertEquals(10,dl.getFoodList().size());
    }
}