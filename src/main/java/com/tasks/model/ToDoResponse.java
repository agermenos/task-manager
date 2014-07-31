package com.tasks.model;

import com.google.gson.Gson;

/**
 * Created by Alejandro on 7/27/2014.
 */


public class ToDoResponse {
    private boolean accepted;
    private String message;

    public ToDoResponse(String message, boolean accepted) {
        this.accepted = accepted;
        this.message = message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public String getMessage() {
        Gson gson = new Gson();
        return gson.toJson(this.message);
    }
}
