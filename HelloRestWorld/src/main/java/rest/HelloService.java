package rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import Technical_Services.ELocation;
import com.google.gson.JsonObject;

import static rest.DTS.dateToString;

@Path("hello")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloService {

    @GET
    @Path("{id}")
    public String helloWorld(@PathParam("id")int id){
        System.out.println("Hello world! This was a GET request!"); //Tests
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", "Hello World!");
        jsonObject.addProperty("id", id);
        System.out.println("to:\t" + jsonObject.toString()); //Tests
        System.out.println(ELocation.Fridge.toString());
        String date = dateToString(20, 06, 2019);

        System.out.println(date);
        return jsonObject.toString();
    }

}
