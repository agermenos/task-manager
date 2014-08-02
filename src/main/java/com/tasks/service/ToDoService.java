package com.tasks.service;


import com.tasks.dao.ToDoDao;
import com.tasks.model.ToDoResponse;
import com.tasks.model.TodoInstance;

import java.util.List;

/**
 * ToDoService
 * Basic business tier component.
 * @version 1.0
 * @author Alejandro
 */
public class ToDoService {
    public ToDoResponse addToDo(TodoInstance todo){
        ToDoDao toDoDao = new ToDoDao();
        // If the title already exists, error. I thought on updating, but maybe that's too smart for an API?
        if (toDoDao.getToDoByTitle(todo.getTitle()).size()>0) return new ToDoResponse("This Title already exists!", false);
        else {
            if (toDoDao.addToDo(todo)) {
                return new ToDoResponse("TodoInstance inserted in the Database", true);
            } else {
                return new ToDoResponse("TodoInstance failed to be inserted. Please check log!", false);
            }
        }
    }

    public ToDoResponse updateToDo(String title, TodoInstance todo){
        ToDoDao toDoDao = new ToDoDao();
        if (toDoDao.update(title, todo)) {
           return MessageService.sendMessage("The task " + title + " has been updated.");
           // return new ToDoResponse("The task " + title + " has been updated.", true);
        } else {
           return new ToDoResponse("TodoInstance failed to be updated. Please check log!", false);
        }
    }

    public ToDoResponse deleteToDo (String title) {
        ToDoDao toDoDao = new ToDoDao();
        List<TodoInstance> todos = toDoDao.getToDoByTitle(title);
        if (todos.size() > 0) {
            if (toDoDao.delete(todos.get(0))) {
                return new ToDoResponse("TodoInstance has been deleted", true);
            }
            else {
                return new ToDoResponse("Found a TodoInstance, but could not delete it", false);
            }
        }
        else {
            return new ToDoResponse("There is no TodoInstance with that title", false);
        }
    }

    public List<TodoInstance> getToDoByTitle(String title){
        List<TodoInstance> todos;
        ToDoDao toDoDao= new ToDoDao();
        if (title.toLowerCase().equals("all")) {
            todos = toDoDao.getAll();
        }
        else {
            todos =  toDoDao.getToDoByTitle(title);
        }
        return todos;
    }

    public ToDoResponse tapToDo(String title) {
        ToDoDao toDoDao = new ToDoDao();
        List<TodoInstance> todos = toDoDao.getToDoByTitle(title);
        if (todos.size() > 0) {
            TodoInstance todo = todos.get(0);
            todo.setDone(!todo.isDone());
            if (toDoDao.update(title, todo)) {
                if (todo.isDone()) {
                    return MessageService.sendMessage("The task " + todo.getTitle() + " has been done.");
                    //return new ToDoResponse("The task " + todo.getTitle() + " has been done.", true);
                }
                else
                    return new ToDoResponse("TodoInstance has been undone", true);
            }
            else {
                return new ToDoResponse("TodoInstance found, but not tapped. Please check logs.", false);
            }
        }
        else {
            return new ToDoResponse("There is no TodoInstance with that title", false);
        }
    }
}



