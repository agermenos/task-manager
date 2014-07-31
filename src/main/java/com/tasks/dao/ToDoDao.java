package com.tasks.dao;


import com.mongodb.*;
import com.tasks.helpers.PojoToBDObjectConverter;
import com.tasks.model.TodoInstance;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * ToDoDao
 * Data Access Object to control DB operations on the TodoInstance Collection
 * @author Alejandro
 * @version 1.0
 */
public class ToDoDao extends BaseDao {
    public boolean addToDo(TodoInstance todo){
         try{
             DBCollection todoDB = getToDoTable();
             BasicDBObject document = PojoToBDObjectConverter.convert(todo);
             todoDB.insert(document);
         }
         catch (Exception e){
             closeMongo();
             e.printStackTrace();
             return false;
         }
         return true;
    }
    public List<TodoInstance> getToDoByTitle (String title){
         try {
             DBCollection todoDB = getToDoTable();
             List<TodoInstance> todos = new ArrayList<TodoInstance>();

             BasicDBObject searchQuery = new BasicDBObject();
             searchQuery.put("title", title);
             DBCursor cursor = todoDB.find(searchQuery);

             while (cursor.hasNext()) {
                 todos.add(PojoToBDObjectConverter.convert(cursor.next()));
             }
             return todos;
         }
         catch(Exception e) {
            closeMongo();
            e.printStackTrace();
            return null;
         }
    }
    public List<TodoInstance> getAll(){
        try {
            DBCollection todoDB = getToDoTable();
            List<TodoInstance> todos = new ArrayList<TodoInstance>();
            DBCursor cursor = todoDB.find();

            while (cursor.hasNext()) {
                todos.add(PojoToBDObjectConverter.convert(cursor.next()));
            }
            return todos;
        }
        catch(Exception e) {
            closeMongo();
            e.printStackTrace();
            return null;
        }
    }
    public boolean update(String title, TodoInstance todo){
        try {
            DBCollection table = getToDoTable();
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("title", title);
            DBCursor cursor = table.find(searchQuery);

            BasicDBObject original = (BasicDBObject) cursor.next();
            BasicDBObject updated = PojoToBDObjectConverter.convert(todo);

            BasicDBObject updateObj = new BasicDBObject();
            updateObj.put("$set", updated);

            table.update(original, updateObj);
            return true;
        }
        catch (Exception e) {
            closeMongo();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(TodoInstance todo){
        try {
            DBCollection table = getToDoTable();
            BasicDBObject todoDBObject = PojoToBDObjectConverter.convert(todo);
            table.remove(todoDBObject);
            return true;
        }
        catch (Exception e) {
            closeMongo();
            e.printStackTrace();
            return false;
        }
    }
    private DBCollection getToDoTable() throws UnknownHostException {
        DB db = getDB();
        DBCollection table = db.getCollection("todo");
        return table;
    }

}
