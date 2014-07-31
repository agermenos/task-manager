package com.tasks.rest;



import com.tasks.model.ToDoResponse;
import com.tasks.model.TodoInstance;
import com.tasks.service.MessageService;
import com.tasks.service.SearchService;
import com.tasks.service.ToDoService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Administrative services
 * Does some of the basic administrative tasks for the TodoInstance lists:
 * - Indexing
 * - Changing the SMS phone number
 * - Boosting the Index Ratios
 */
@Path("/admin")
public class AdminREST {

    @POST
    @Path("/index")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createIndexInJSON_Empty() {
        SearchService searchService = new SearchService();
        ToDoService toDoService = new ToDoService();
        List<TodoInstance> todos = toDoService.getToDoByTitle("all");
        ToDoResponse response = searchService.indexAll(todos);
        if (response.isAccepted()) {
            return Response.status(Response.Status.OK).entity(response.getMessage()).build();
        }
        else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response.getMessage()).build();
        }
    }

    @POST
    @Path("/phone/{phoneNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response setPhoneNumberInJSON_Empty(@PathParam("phoneNumber") String phoneNumber) {
        MessageService.setToPhoneNumber(phoneNumber);
        ToDoResponse response = new ToDoResponse("TO Phone Number Updated",true);
        return Response.status(Response.Status.ACCEPTED).entity(response.getMessage()).build();
    }
}
