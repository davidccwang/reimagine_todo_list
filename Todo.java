/*
 * Created by Anand on 10/13/2016.
 */
package com.Cloud4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static List<Todo> todos = new ArrayList<Todo>();

    static{
        todos.add(new Todo(1, "Todo task 1", "10/13/2016", 1, Boolean.FALSE));
        todos.add(new Todo(2, "Todo task 2", "10/14/2016", 2, Boolean.TRUE));
        todos.add(new Todo(3, "Todo task 3", "10/15/2016", 3, Boolean.FALSE));
        todos.add(new Todo(4, "Todo task 4", "10/16/2016", 4, Boolean.TRUE));
    }
    
    private Integer id;
    private String todoItem;
    private String createdOn;
    private Integer priority;
    private Boolean completed;
    
    public Todo(Integer id, String todoItem, String createdOn, Integer priority, Boolean completed) {
        super();
        this.id = id;
        this.todoItem = todoItem;
        this.createdOn = createdOn;
        this.priority = priority;
        this.completed = completed;
    }

    public Todo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(String todoItem) {
        this.todoItem = todoItem;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }


    public static List<Todo> getAll(){
        return todos;
    }
}
