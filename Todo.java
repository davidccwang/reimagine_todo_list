package com.Cloud4;

import java.io.Serializable;


/**
 * Updated by Cloud4 on 10/22/2016.
 */

public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

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
}
