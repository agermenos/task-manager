package com.tasks.model;

/**
 * TodoInstance Plain Old Java Object
 * This is the object to be parsed from JSON to Java
 * @author Alejandro Germenos
 * @version 1.0
 */
public class TodoInstance {

    //private int id;
	private String title;
	private String body;
    private boolean done;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public TodoInstance() {
    }

    public TodoInstance(String title, String body, boolean done) {
        this.title = title;
        this.body = body;
        this.done = done;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "TodoInstance{" +
               "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", done=" + done +
                '}';
    }
}
