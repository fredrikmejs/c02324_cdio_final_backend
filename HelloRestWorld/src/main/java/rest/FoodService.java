package rest;

import Backend.Backend;
import Technical_Services.ECategory;
import Technical_Services.ELocation;
import Technical_Services.FoodDTO;
import Technical_Services.IFoodDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.javaws.exceptions.InvalidArgumentException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("food")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoodService {
    Backend backend = new Backend();
    //Dummy data for local testing
    static Map<Integer, FoodDTO> foodDTOMap = new HashMap<>();
    static {
        FoodDTO food1 = new FoodDTO();
        food1.setID(1);
        food1.setName("Smør");
        food1.setExpDate(new Date(System.currentTimeMillis()));
        food1.setCategory(ECategory.Dairy);
        food1.setLocation(ELocation.Fridge);
        foodDTOMap.put(1, food1);
    }
    //TODO: Implement SQL support

    //GET request from frontend receives a JSON array of JSON objects as a String
    @GET
    public String getAllFoods() throws SQLException {
        List<IFoodDTO> foodList = new ArrayList<>();
        JsonArray jsonArray = new JsonArray();
        for(int i = 0; i < foodList.size(); i++){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", foodList.get(i).getID());
            jsonObject.addProperty("name", foodList.get(i).getFoodName());
            jsonObject.addProperty("expDate", foodList.get(i).getExpDate().toString());
            jsonObject.addProperty("category", foodList.get(i).getCategory().name());
            jsonObject.addProperty("location", foodList.get(i).getLocation().name());
            jsonArray.add(jsonObject);
        }
        return jsonArray.toString();
    }
//TODO: Implement SQL support

    //This method should return a specified food
    @GET
    @Path("{id}")
    public String getFood(@PathParam("id") int id){
        FoodDTO food = foodDTOMap.get(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", food.getID());
        jsonObject.addProperty("name", food.getFoodName());
        jsonObject.addProperty("expDate", food.getExpDate().toString());
        String foodCat = food.getCategory().toString();
        String foodLoc = food.getLocation().toString();
        jsonObject.addProperty("category", foodCat);
        jsonObject.addProperty("location", foodLoc);
        System.out.println(jsonObject.toString());
        return jsonObject.toString();
    }
//TODO: Implement SQL support
    @POST
    public Response createFood(FoodDTO foodDTO){
        System.out.println("Post succeeded!");
        if(foodDTOMap.putIfAbsent(foodDTO.getID(), foodDTO) == null){
            return Response.status(201).entity("Food created!").build();
        }else{
            Response response1 = Response.status(Response.Status.BAD_REQUEST)
                    .entity("ID: " + foodDTO.getID() + " already in use!")
                    .build();
            throw new WebApplicationException(response1);
        }
    }
//TODO: Implement SQL support
    @DELETE
    @Path("{id}")
    public Response deleteFood(@PathParam("id") int id){
        IFoodDTO food = foodDTOMap.get(id);
        if(food != null){
            foodDTOMap.remove(id);
            return Response.status(200).entity("Deletion successful").build();
        }else{
            return Response.status(404).entity("Food not found..").build();
        }
    }
//TODO: Implement SQL support
    @PUT
    @Path("{id}")
    public Response updateFood(@PathParam("id") int id, FoodDTO food) {
        FoodDTO updatedFood = foodDTOMap.get(id);
        //TODO: Check up on method toLocalDate();
        try {
            if (food.getFoodName() != null) {
                updatedFood.setName(food.getFoodName());
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
        }catch(Exception e){
            return Response.status(400).entity("Update failed!").build();
        }
        return Response.status(200).entity("Food updated").build();
    }



}
