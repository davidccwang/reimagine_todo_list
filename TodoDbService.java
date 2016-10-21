package com.Cloud4;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.mindrot.jbcrypt.BCrypt;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import java.util.List;

/**
 * Created by Anand on 10/21/2016.
 */
public class TodoDbService {

    MongoClient todoClient = new MongoClient("localhost", 27017);
    Datastore todoDatastore = new Morphia().createDatastore(todoClient, "todos");

    public List<Todo> getTodos (String username, String password) {
        System.out.println("Username is: " + username + " Password is: " + password);
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());
        System.out.println("Username is: " + username + " Hashed Password is: " + hashedPassword);
        DB db = todoDatastore.getDB();
        //db.(username,hashedPassword.toCharArray());
        List<Todo> todoList = todoDatastore.createQuery(Todo.class).asList();
        return todoList;
    }

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
        System.out.println("Entering createTodo");
        Integer id = todo.getId();
        System.out.println("Id: " + id + " TodoTask: " + todo.getTodoItem());
        if (this.getTodo(id) != null) {
            System.out.println("Id: " + id + " TodoTask: " + todo.getTodoItem() + " Already present");
            return("Id: " + id + " TodoTask: " + todo.getTodoItem() + " Already present");
        } else {
            todoDatastore.save(todo);
            return "Todo created";
        }
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
