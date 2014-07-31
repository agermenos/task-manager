package com.tasks.helpers;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.tasks.model.TodoInstance;

/**
 * Created by Alejandro on 7/25/2014.
 */
public class PojoToBDObjectConverter {
    public static BasicDBObject convert(TodoInstance todo){
        BasicDBObject object= new BasicDBObject();
        object.put("title", todo.getTitle());
        object.put("body", todo.getBody());
        object.put("done", todo.isDone());
        return object;
    }
    public static TodoInstance convert(DBObject object){
        TodoInstance todo = new TodoInstance();
        todo.setTitle(object.get("title").toString());
        todo.setBody(object.get("body").toString());
        todo.setDone(object.get("done").toString().equals("true") ? true : false);
        return todo;
    }
}
