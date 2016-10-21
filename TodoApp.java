/*
 * TodoApp class
 * Updated by Anand on 10/21/2016.
 */
package com.Cloud4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;
import java.util.Base64;
import static spark.Spark.post;

public class TodoApp
{
    public static TodoDbService todoDbService = new TodoDbService();
    
    private static Gson GSON = new GsonBuilder().create();

    public static void main( String[] args )
    {
        System.out.println("This is the main - begin");

        Spark.before((request,response)->{
            System.out.println("Entering before method");
            String method = request.requestMethod();
            if(method.equals("GET") || method.equals("POST") || method.equals("PUT") || method.equals("DELETE")){
                System.out.println("Method is :" + method);

                String authorizationData = request.headers("Authorization");
                System.out.println("Authorization received: " + authorizationData);
                String receivedCredentials = authorizationData.substring(6); /* After "Basic " */
                System.out.println("receivedCredentials: " + receivedCredentials);

                String expectedUserPass = "anand:anand123";
                String expectedCredentials = Base64.getEncoder().encodeToString(expectedUserPass.getBytes());
                System.out.println("expectedCredentials: " + expectedCredentials);

                if (expectedCredentials.equals(receivedCredentials)) {
                    System.out.println("Credentials are OK");
                } else {
                    System.out.println("Credentials are NOT OK");
                    Spark.halt(401, "User Unauthorized");
                }
            }
            System.out.println("Exiting before: Method is :" + method);
        });
        
        Spark.get("/api/todos",  (request, response) -> {
            System.out.println("Entering get todos");
                    List<Todo> todos = todoDbService.getTodos("anand","anand123");
                    return GSON.toJson(todos);
        });
        
        Spark.get("/api/todos/:id",  (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            Todo todo = todoDbService.getTodo(id);
            return GSON.toJson(todo);
        });
        
        post("/api/todos",  (request, response) -> {
            System.out.println("1111111111111111111");
            response.type("application/json");

            Todo toStore = null;
            try {
                toStore = GSON.fromJson(request.body(), Todo.class);
            } catch (JsonSyntaxException e) {
                response.status(400);
                return "INVALID JSON";
            }
            System.out.println("2222222222");
            todoDbService.createTodo(toStore);
            System.out.println("333333333333333");
            return GSON.toJson(toStore);
        });
        
         Spark.put("/api/todos/:id",  (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            if (id == null) {
                System.out.println("4444444444444");
                response.status(404);
                return "NOT_FOUND";
            }else{
                Todo toStore = null;
                try {
                    toStore = GSON.fromJson(request.body(), Todo.class);
                    System.out.println("The id is: " + id);
                    System.out.println("The getId is: " + toStore.getId());
                } catch (JsonSyntaxException e) {
                    response.status(400);
                    return "INVALID JSON";
                }
                todoDbService.updateTodo(toStore);
                return GSON.toJson(toStore);
            }
        });
        
        Spark.delete("/api/todos/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            Todo todo = todoDbService.getTodo(id);
            if(todo == null){
                System.out.println("555555555555555");
                response.status(404);
                return "NOT_FOUND";
            }else{
                todoDbService.deleteTodo(todo);
                return GSON.toJson(todo);
            }
        });
        System.out.println("This is the main - end");
    }
}
