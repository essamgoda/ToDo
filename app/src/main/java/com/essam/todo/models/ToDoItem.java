package com.essam.todo.models;


/**
 * Created by essam on 09/02/17.
 */

/**
 * The ToDoItem class is a todo models.
 */
public class ToDoItem {
    private String key;
    private String todo;
    private Boolean isFinished;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }


    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }
}
