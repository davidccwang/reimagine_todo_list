/*
 * This is the initial TodoApp class - still work in progress
 * Created by Anand on 10/13/2016.
 */
package com.Cloud4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;

import static spark.Spark.post;
import static spark.Spark.put;

public class TodoApp
{
    public static TodoDbService todoDbService = new TodoDbService();
    
    private static Gson GSON = new GsonBuilder().create();

    public static void main( String[] args )
    {
        Spark.get("/todo/:id",  (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            Todo todo = todoDbService.getTodo(id);
            return GSON.toJson(todo);
        });
        
        post("/api/todo",  (request, response) -> {
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

        Spark.put("/api/todo/:id",  (request, response) -> {
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

        Spark.delete("/api/todo/:id", (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            Todo todo = todoDbService.getTodo(id);
            if(todo == null){
                System.out.println("555555555555555");
                response.status(404);
                return "NOT_FOUND";
            } else {
                todoDbService.deleteTodo(todo);
                return GSON.toJson(todo);
            }
        });
        System.out.println("This is the main - end");
    }
}
