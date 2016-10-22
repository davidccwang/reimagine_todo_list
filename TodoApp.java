package com.Cloud4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;

import java.util.Base64;
import java.util.List;

import static spark.Spark.post;

/**
 * Updated by Cloud4 on 10/22/2016.
 */

public class TodoApp
{

    public static TodoDbService todoDbService = new TodoDbService();

    private static Gson GSON = new GsonBuilder().create();

    public static void main( String[] args )
    {
        System.out.println("This is the main - begin " + MySte.location());

        /* Authenticate the user for every endpoint into this application */
        Spark.before((request,response)->{
            System.out.println(MySte.location());
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
            System.out.println("Exiting before: HTTP request is :" + method);
        });

        Spark.get("/api/todos",  (request, response) -> {
            System.out.println("Entering get todos");
            List<Todo> todos = todoDbService.getTodos("anand","anand123");
            if (todos.isEmpty()) {
                response.status(404);
                return ("No Records FOUND");
            }
            return GSON.toJson(todos);
        });

        Spark.get("/api/todos/:id",  (request, response) -> {
            System.out.println("Entering get todos/:id");
            Integer id = Integer.parseInt(request.params("id"));
            Todo todo = todoDbService.getTodo(id);
            if (todo == null) {
                response.status(404);
                return ("Id " + id + " NOT FOUND");
            }
            return GSON.toJson(todo);
        });

        post("/api/todos",  (request, response) -> {
            System.out.println("Entering post todos");
            System.out.println(MySte.location());
            response.type("application/json");

            Todo toStore = null;
            try {
                toStore = GSON.fromJson(request.body(), Todo.class);
            } catch (JsonSyntaxException e) {
                response.status(400);
                return "INVALID JSON";
            }
            System.out.println(MySte.location());
            todoDbService.createTodo(toStore);
            System.out.println(MySte.location());
            response.status(201);
            return GSON.toJson(toStore);
        });

        Spark.put("/api/todos/:id",  (request, response) -> {
            System.out.println("Entering put todos/:id");
            Integer id = Integer.parseInt(request.params("id"));
            if (id == null) { /* If the id could not be obtained from the request; then error*/
                System.out.println(MySte.location());
                response.status(404);
                return "NOT_FOUND";
            } else { /* The id is present; then update and save */
                Todo toStore = null;
                try {
                    toStore = GSON.fromJson(request.body(), Todo.class);
                    System.out.println("The id from the request is: " + id);
                    System.out.println("The id from the body is: " + toStore.getId());
                } catch (JsonSyntaxException e) {
                    response.status(400);
                    return "INVALID JSON";
                }
                todoDbService.updateTodo(toStore);
                return GSON.toJson(toStore);
            }
        });

        Spark.delete("/api/todos/:id", (request, response) -> {
            System.out.println("Entering delete todos/:id");
            Integer id = Integer.parseInt(request.params("id"));
            Todo todo = todoDbService.getTodo(id);
            if(todo == null){
                System.out.println(MySte.location());
                response.status(404);
                return "NOT_FOUND";
            }else{
                todoDbService.deleteTodo(todo);
                return GSON.toJson(todo);
            }
        });

        System.out.println("This is the main - end");
        System.out.println(MySte.location());
    }
}
