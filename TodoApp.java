package com.Cloud4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import spark.Spark;

/**
 * Created by alpha on 10/13/2016.
 */

public class TodoApp
{

    private static Gson GSON = new GsonBuilder().create();

    public static void main( String[] args )
    {
        Spark.get("/todo/:id",  (request, response) -> {
            Integer id = Integer.parseInt(request.params("id"));
            return GSON.toJson(Todo.get(id));
        });
