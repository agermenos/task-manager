package tests;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import com.tasks.dao.ToDoDao;
import com.tasks.model.TodoInstance;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestGet {

    @Test
    public void testAllDao() {
        boolean retVal = true;
        ToDoDao tdDao = new ToDoDao();
        List<TodoInstance> todos = tdDao.getAll();
        for (TodoInstance todo:todos) {
            System.out.println(todo);
        }

        Assert.assertEquals(retVal, true);
    }

    @Test
    public void testOneDao() {
        boolean retVal = true;
        ToDoDao tdDao = new ToDoDao();
        List<TodoInstance> todos = tdDao.getToDoByTitle("Test 83");
        for (TodoInstance todo:todos) {
            System.out.println(todo);
        }

        Assert.assertEquals(retVal, true);
    }

	public void testService () {
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("http://localhost:8080/todos/get");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

    public static void main(String args[]){
        TestGet tget = new TestGet();
        tget.testAllDao();
        //tget.testOneDao();
    }

}