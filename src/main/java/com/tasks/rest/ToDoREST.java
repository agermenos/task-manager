package com.tasks.rest;

import com.tasks.helpers.PATCH;
import com.tasks.model.ToDoResponse;
import com.tasks.model.TodoInstance;
import com.tasks.service.SearchService;
import com.tasks.service.ToDoService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.List;

/**
 * JSONService Jersey entry point for the todos project
 * This class is the facade of the business.
 * It will call a TodoManager, which is responsible for the business tasks
 * @author Alejandro Germenos
 * @version 1.0
 */
@Path("/todos")
public class ToDoREST {
    private ToDoService toDoService=new ToDoService();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToDoInJSON_Empty() {
        return getToDoInJSON("all");
    }

    @GET
	@Path("/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getToDoInJSON(@PathParam("title") String title) {
        List<TodoInstance> todos=toDoService.getToDoByTitle(title);
        return Response.status(Response.Status.OK).entity(todos).build();
	}

    @GET
    @Path("/search/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchToDoJSON(@PathParam("title") String title) {
        SearchService searchService = new SearchService(SearchService.Strategy.MULTIPLE);
        List<TodoInstance> responses=searchService.searchToDoTitle(title);
        return Response.status(Response.Status.OK).entity(responses).build();
    }

    @PATCH
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tapToDoJSON(@PathParam("title") String title) {
        ToDoResponse response = toDoService.tapToDo(title);
        if (response.isAccepted()) {
            return Response.status(Response.Status.OK).entity(response.getMessage()).build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.getMessage()).build();
        }
    }

    @POST
    @Path("/tap/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response tapFacadeToDoJSON(@PathParam("title") String title) {
        return tapToDoJSON(title);
    }

    @DELETE
    @Path("/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteToDoInJSON(@PathParam("title") String title) {
        ToDoResponse response = toDoService.deleteToDo(title);
        if (response.isAccepted()) {
            return Response.status(Response.Status.OK).entity(response.getMessage()).build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.getMessage()).build();
        }
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertToDo( TodoInstance todo ) {
        ToDoResponse response = toDoService.addToDo(todo);
        if (response.isAccepted()) {
            return Response.status(Response.Status.CREATED).entity(response.getMessage()).build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.getMessage()).build();
        }
    }

    @PUT
    @Path("/{title}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateToDo(@PathParam("title") String title, TodoInstance todo) {
        ToDoResponse response = toDoService.updateToDo(title, todo);
        if (response.isAccepted()) {
            return Response.status(Response.Status.CREATED).entity(response.getMessage()).build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.getMessage()).build();
        }
    }


	
}