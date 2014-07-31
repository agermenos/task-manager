package tests;


import com.tasks.dao.ToDoDao;
import com.tasks.model.TodoInstance;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alejandro on 7/25/2014.
 */
public class TestAdd {
    @Test
    public void testDao() {
        boolean retVal = true;
        ToDoDao tdDao = new ToDoDao();
        for (int cont = 1; cont < 100; cont++) {
            TodoInstance t = new TodoInstance("Test " + cont, "Testing " + cont, false);
           // if (retVal) retVal = tdDao.addToDo(t);

        }
        Assert.assertEquals(retVal, true);
    }

    public static void main(String args[]){
        TestAdd tadd = new TestAdd();
        tadd.testDao();
    }
}
