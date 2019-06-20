package rest;


import Technical_Services.FoodDTO;
import com.google.gson.JsonObject;

import javax.ws.rs.core.Response;

public interface IFoodService {
    Response getAllFoods(String username, int location);
    Response getFood(int id, String userName);
    Response createFood(FoodDTO foodDTO);
    Response deleteFood(int id, String userName);
    Response deleteAllFood(String userName, String location);
    Response updateFood(int id, String userName, FoodDTO food);
    Response getExpiredFoods(String userName, int days);
    Response verifyUser(String userName);
    Response addUser(String userName);
    Response deleteUser(String userName);
}
