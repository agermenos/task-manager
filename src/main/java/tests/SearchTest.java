package tests;

import com.tasks.model.TodoInstance;
import com.tasks.service.SearchService;


import java.util.List;

/**
 * Created by Alejandro on 7/30/2014.
 */


public class SearchTest {
    public static void main(String args[]){
        SearchTest st = new SearchTest();
        st.testSearch();
    }

    private void testSearch(){
        SearchService searchService=new SearchService(SearchService.Strategy.MULTIPLE);
      /**  List<QueryResponse> responses=searchService.deprecatedSearchToDoTitle("1");
        for (QueryResponse qr:responses){
            System.out.println(qr.toString());
        } */
        List<TodoInstance> todos = searchService.searchToDoTitle("4");
        if (todos!=null){
            for (TodoInstance todo:todos){
                System.out.println(todo);
            }
        }
    }


}
