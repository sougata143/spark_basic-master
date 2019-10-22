package app;

import static spark.Spark.port;

import java.util.List;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.user.User;
import app.user.UserService;

public class Application {

	private static UserService userService = new UserService();
    private static ObjectMapper om = new ObjectMapper();
 
    public static void main(String[] args) {
 
        // Start embedded server at this port
        port(8081);
 
        // Main Page, welcome
        get("/", (request, response) -> "Welcome");
 
        // POST - Add an user
        post("/user/add", (request, response) -> {
 
            String name = request.queryParams("name");
            String email = request.queryParams("email");
            User user = userService.add(name, email);
            response.status(201); // 201 Created
            return om.writeValueAsString(user);
 
        });
 
        // GET - Give me user with this id
        get("/user/:id", (request, response) -> {
            User user = userService.findById(request.params(":id"));
            if (user != null) {
                return om.writeValueAsString(user);
            } else {
                response.status(404); // 404 Not found
                return om.writeValueAsString("user not found");
            }
        });
 
        // Get - Give me all users
        get("/user", (request, response) -> {
            List result = userService.findAll();
            if (result.isEmpty()) {
                return om.writeValueAsString("user not found");
            } else {
                return om.writeValueAsString(userService.findAll());
            }
        });
 
        // PUT - Update user
        put("/user/:id", (request, response) -> {
            String id = request.params(":id");
            User user = userService.findById(id);
            if (user != null) {
                String name = request.queryParams("name");
                String email = request.queryParams("email");
                userService.update(id, name, email);
                return om.writeValueAsString("user with id " + id + " is updated!");
            } else {
                response.status(404);
                return om.writeValueAsString("user not found");
            }
        });
 
        // DELETE - delete user
        delete("/user/:id", (request, response) -> {
            String id = request.params(":id");
            User user = userService.findById(id);
            if (user != null) {
                userService.delete(id);
                return om.writeValueAsString("user with id " + id + " is deleted!");
            } else {
                response.status(404);
                return om.writeValueAsString("user not found");
            }
        });
 
    }

}
