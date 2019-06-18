package rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import Technical_Services.ELocation;
import com.google.gson.JsonObject;

import static rest.DTS.dateToString;

@Path("hello")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloService {
    /**
     * Method is to test REST functionality on a most basic level.
     * @param id ID is the ID to be printed.
     * @return returns HTTP status code 200 and a JSON object
     */
    @GET
    @Path("{id}")
    public Response helloWorld(@PathParam("id")int id){
//        Prints Hello World!.. to the console
        System.out.println("Hello world! This was a GET request!"); //Tests
//        Creates a JsonObject and adds properties to it
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", "Hello World!");
        jsonObject.addProperty("id", id);
//        Prints the JsonObject to the console
        System.out.println("to:\t" + jsonObject.toString()); //Tests
//        Returns HTTP response status 200 and a JsonObject
        return Response.status(200).entity(jsonObject.toString()).build();
    }

}
