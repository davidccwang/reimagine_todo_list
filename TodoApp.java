/*
 * This is the initial TodoApp class - still work in progress
 * Created by Anand on 10/13/2016.
 */
package com.Cloud4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;


public class TodoApp
{

    private static Gson GSON = new GsonBuilder().create();

    public static void main( String[] args )
    {
        Spark.get("/todo/:id",  (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            return GSON.toJson(Todo.get(id));
        });
    }
}
