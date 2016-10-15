package com.Cloud4;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpha on 10/13/2016.
 */

public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static List<Todo> todos = new ArrayList<Todo>();

    static{
        todos.add(new Todo(1, "Todo task 1", "10/13/2016", 1, Boolean.FALSE));
        todos.add(new Todo(2, "Todo task 2", "10/14/2016", 2, Boolean.TRUE));
        todos.add(new Todo(3, "Todo task 3", "10/15/2016", 3, Boolean.FALSE));
        todos.add(new Todo(4, "Todo task 4", "10/16/2016", 4, Boolean.TRUE));
    }
