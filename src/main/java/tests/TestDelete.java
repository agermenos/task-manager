package tests;


import com.tasks.dao.ToDoDao;
import com.tasks.model.TodoInstance;
import org.junit.Assert;
import org.junit.Test;

public class TestDelete {

    @Test
    public void testDao() {
        boolean retVal = true;
        ToDoDao tdDao = new ToDoDao();
        TodoInstance todo = tdDao.getToDoByTitle("Test 10").get(0);
        tdDao.delete(todo);
        Assert.assertEquals(retVal, true);
    }

    public static void main(String args[]){
        TestDelete tdel = new TestDelete();
        tdel.testDao();
    }

}
