package com.Cloud4;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by Anand on 10/18/2016.
 */
public class TodoDbService {
    MongoClient todoClient = new MongoClient("localhost", 27017);
    Datastore todoDatastore = new Morphia().createDatastore(todoClient, "todos");

    public Todo getTodo (Integer id) {
        System.out.println("aaaaaaaaaaaaaa");
        Todo todo = todoDatastore.find(Todo.class, "id", id).get();
        if (todo != null) {
            return todo;
        } else  {
            return null;
        }
    }

    public String createTodo (Todo todo) {
        System.out.println("bbbbbbbbbbbbb");
        System.out.println(todo.getId() + todo.getTodoItem());
        todoDatastore.save(todo);
        return "Todo created";
    }

    public String updateTodo (Todo todo) {
        System.out.println("ccccccccccc");
        System.out.println(todo.getId() + " : " + todo.getTodoItem());
        Query<Todo> query = todoDatastore.createQuery(Todo.class).field("id").equal(todo.getId());
        UpdateOperations<Todo> ops = todoDatastore.createUpdateOperations(Todo.class).set("completed", todo.getCompleted()).
                set("priority", todo.getPriority());
        todoDatastore.update (query, ops);
        return "Todo updated";
    }

    public void deleteTodo(Todo todo){
        System.out.println("Deleting " + todo.getId());
        Query<Todo> query = todoDatastore.createQuery(Todo.class).field("id").equal(todo.getId());
        todoDatastore.delete(query);
        System.out.println("Deleted " + todo.getId());
    }
}

