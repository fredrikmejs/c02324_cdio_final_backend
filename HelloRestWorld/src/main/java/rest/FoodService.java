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

@Path("food/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodService {
    ErrorHandling errorHandling = new ErrorHandling();
    //Dummy data for local testing
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
    //GET request from frontend receives a JSON array of JSON objects as a String
    @GET
    @Path("{userName}/get")
    public Response getAllFoods(@PathParam("userName") String userName) {
        List<IFoodDTO> foodList;
        try {
            foodList = errorHandling.getFoodList(userName);
            JsonArray jsonArray = new JsonArray();
            for (int i = 0; i < foodList.size(); i++) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("id", foodList.get(i).getID());
                jsonObject.addProperty("name", foodList.get(i).getFoodName());
                jsonObject.addProperty("expDate", foodList.get(i).getExpDate().toString());
                jsonObject.addProperty("category", foodList.get(i).getCategory().name());
                jsonObject.addProperty("location", foodList.get(i).getLocation().name());
                jsonArray.add(jsonObject);
            }
            return Response.status(200).entity(jsonArray.toString()).build();
        }catch(SQLException e){
            return Response.status(400).build();
        }
    }
    //This method should return a specified food
    @GET
    @Path("{userName}/get/{id}")
    public Response getFood(@PathParam("id") int id, @PathParam("userName") String userName){
        IFoodDTO food;
        JsonObject jsonObject = new JsonObject();
        try {
            food = errorHandling.getFoodItem(userName, id);
            jsonObject.addProperty("id", food.getID());
            jsonObject.addProperty("name", food.getFoodName());
            jsonObject.addProperty("expDate", food.getExpDate().toString());
            jsonObject.addProperty("category", food.getCategory().name());
            jsonObject.addProperty("location", food.getLocation().name());

            return Response.status(200).entity(jsonObject.toString()).build();
        } catch (SQLException e) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("{userName}")
    public Response createFood(FoodDTO foodDTO){
        try {
            errorHandling.addFood(foodDTO);
            return Response.status(201).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Something went wrong!").build();
        }
    }
    @DELETE
    @Path("{userName}/{id}")
    public Response deleteFood(@PathParam("id") int id, @PathParam("userName") String userName){
        boolean success;
        try {
            success = errorHandling.deleteFood(userName, id);
            if(success) {
                return Response.status(200).build();
            }else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    @PUT
    @Path("{userName}/{id}")
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
        }catch(Exception e){
            return Response.status(400).entity("Update failed!").build();
        }
        return Response.status(200).entity("Food updated").build();
    }



}
