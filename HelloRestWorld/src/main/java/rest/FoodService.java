package rest;

import Backend.Backend;
import Domain.ErrorHandling;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//Path to this class is now
@Path("food/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodService {
    ErrorHandling errorHandling = new ErrorHandling();

//    Dummy data for local testing---------------------------------
    static Map<Integer, IFoodDTO> foodDTOMap = new HashMap<>();
    static {
        FoodDTO food1 = new FoodDTO();
        food1.setID(1);
        food1.setFoodName("Smør");
        food1.setExpDate(new Date(System.currentTimeMillis()));
        food1.setCategory(ECategory.Dairy);
        food1.setLocation(ELocation.Fridge);
        foodDTOMap.put(1, food1);
    }
//    END of dummy data---------------------------------------------

    /**
     * This is a method to handle the HTTP get request to get all foods belonging to a certain user.
     * @param userName This parameter is given in the HTTP GET request URL and is used to specify which user to access
     * @return The method returns a HTTP response of either code=200 (success) + a body containing a JSON-array, or a HTTP response code=400 (BAD_REQUEST)
     */
    //GET request from frontend receives a JSON array of JSON objects as a String
    @GET
    @Path("{userName}/get")
    public Response getAllFoods(@PathParam("userName") String userName) {
        List<IFoodDTO> foodList;
        try {
            //Retrieve a list of FoodDTO objects
            foodList = errorHandling.getFoodList(userName);
            JsonArray jsonArray = new JsonArray();
            //Add the data from the elements of the list of FoodDTO objects to a JSON object List<IFoodDTO> -> JsonObject -> JsonArray
            for (int i = 0; i < foodList.size(); i++) {
                JsonObject jsonObject = new JsonObject();
                //Add each parameter in the FoodDTO object as a property to the JsonObject
                jsonObject.addProperty("id", foodList.get(i).getID());
                jsonObject.addProperty("name", foodList.get(i).getFoodName());
                jsonObject.addProperty("expDate", foodList.get(i).getExpDate().toString());
                jsonObject.addProperty("category", foodList.get(i).getCategory().name());
                jsonObject.addProperty("location", foodList.get(i).getLocation().name());
                //Add the JsonObject to the JsonArray
                jsonArray.add(jsonObject);
            }
            //If no errors occurred return status 200 and the JsonArray as a String
            return Response.status(200).entity(jsonArray.toString()).build();
        }catch(SQLException e){
            //If an error with the database occurred, catch exception and return status 400
            return Response.status(400).build();
        }
    }

    /**
     * This method accepts a HTTP GET request and grabs a food_id and userName from the URL
     * Which is then used to access the database and acquire a specific food item belonging to the user.
     * If it succeeds it returns a HTTP response status of 200 (success) and the requested food item as a JsonObject.
     * If it fails the return is a HTTP response status of 404 (File not found).
     * @param id ID of the food being requested
     * @param userName userName for the user requesting the food item
     * @return HTTP status 200 (success) or 404 (failure -> File not found)
     */
    @GET
    @Path("{userName}/get/{id}")
    public Response getFood(@PathParam("id") int id, @PathParam("userName") String userName){
        IFoodDTO food;
        JsonObject jsonObject = new JsonObject();
        try {
            //Access database to retrieve sinle item belonging to the user
            food = errorHandling.getFoodItem(userName, id);
            jsonObject.addProperty("id", food.getID());
            jsonObject.addProperty("name", food.getFoodName());
            jsonObject.addProperty("expDate", food.getExpDate().toString());
            jsonObject.addProperty("category", food.getCategory().name());
            jsonObject.addProperty("location", food.getLocation().name());
            //Returns reponse 200 (success) and the requested JsonObject
            return Response.status(200).entity(jsonObject.toString()).build();
        } catch (SQLException e) {
            e.printStackTrace();
            //Returns response 404 (file not found)
            return Response.status(404).build();
        }
    }

    /**
     * This method creates a food in the database from the parameters given via the HTTP POST request.
     * @param foodDTO This parameter is given by the HTTP POST request and represents the food to be created.
     * @return Returns HTTP response status 201 (created) on a success, and status 400 (BAD_REQUEST) on a failure.
     */
    @POST
    public Response createFood(FoodDTO foodDTO){
        try {
//            Accesses the database to add the provided food item
            errorHandling.addFood(foodDTO);
//            returns status 201 if operation was successful
            return Response.status(201).build();
        } catch (SQLException e) {
            e.printStackTrace();
//            returns status 400/BAD_REQUEST if operation failed.
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong!").build();
        }
    }

    /**
     * This method delete a specific food item from the given users list of foods. Method returns HTTP response status 200 on a successful deletion,
     * and HTTP response status 404 on a failure (if item was not found)
     * @param id The ID of the given food to be deleted
     * @param userName The userName of the user wishing to delete an item
     * @return returns HTTP status 200 on success, or HTTP status 404 on a failure.
     */
    @DELETE
    @Path("{userName}/delete/{id}")
    public Response deleteFood(@PathParam("id") int id, @PathParam("userName") String userName){
        try {
//          Access database to delete the food belonging to the user with the given id
            errorHandling.deleteFood(userName, id);
//          returns HTTP status 200 (success) if operation was successful
            return Response.status(200).build();
        } catch (SQLException e) {
            e.printStackTrace();
//            On a failure returns HTTP response status 404, thus assuming the item was not found.
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("{userName}/delete/all/{location}")
    public Response deleteAllFood(@PathParam("userName") String userName, @PathParam("location") String location) {
        if(ELocation.Fridge.name().equals(location)){
            try {
                errorHandling.deleteAll(ELocation.Fridge, userName);
                return Response.status(200).build();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(400).build();
            }
        }else if(ELocation.Freezer.name().equals(location)){
            try {
                errorHandling.deleteAll(ELocation.Freezer, userName);
                return Response.status(200).build();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(400).build();
            }
        }else if(ELocation.Pantry.name().equals(location)){
            try {
                errorHandling.deleteAll(ELocation.Pantry, userName);
                return Response.status(200).build();
            } catch (SQLException e) {
                e.printStackTrace();
                return Response.status(400).build();
            }
        }else{
            return Response.status(404).entity("Location for foods not found").build();
        }
    }

    @PUT
    @Path("{userName}/put/{id}")
    public Response updateFood(@PathParam("id") int id, @PathParam("userName") String userName,FoodDTO food) {
        IFoodDTO updatedFood = new FoodDTO();
        updatedFood.setID(id);
        updatedFood.setUserName(userName);

        try {
            if (food.getFoodName() != null) {
                updatedFood.setFoodName(food.getFoodName());
            }
            if(food.getExpDate() != null){
                updatedFood.setExpDate(food.getExpDate());
            }
            if(food.getCategory() != null){
                updatedFood.setCategory(food.getCategory());
            }
            if(food.getLocation() != null){
                updatedFood.setLocation(food.getLocation());
            }
            foodDTOMap.replace(id, updatedFood);
            errorHandling.updateFood(updatedFood);
            return Response.status(200).entity("Food updated").build();
        }catch(Exception e){
            e.printStackTrace();
            return Response.status(400).entity("Update failed!").build();
        }

    }



}
