package com.tasks.service;

import com.tasks.model.QueryResponse;
import com.tasks.model.ToDoResponse;
import com.tasks.model.TodoInstance;
import io.searchbox.action.BulkableAction;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.indices.CreateIndex;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.FilteredQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import static org.elasticsearch.node.NodeBuilder.*;


import static org.elasticsearch.index.query.QueryBuilders.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * SearchService
 * Indexes (per request), and searches todo's in the Search Engine
 * Runs on title index (se far)
 * @author Alejandro
 * @version 1.0
 */
public class SearchService {

    private JestClient jestClient;
    private String connectionUrl;
    public enum Strategy {BOOSTING, SIMPLE, BOOLEAN, MULTIPLE, OR};
    private Strategy currentStrategy=Strategy.BOOSTING;
    private final String KEY_NAME="todos_access";
    private final String KEY="toakk0m2doq30ffrrszqlvk7tipnft1y";

    public SearchService(Strategy newStrategy){
        currentStrategy = newStrategy;
        connectionUrl = System.getenv("SEARCHBOX_URL");
        if (connectionUrl==null) {
            connectionUrl="https://"+KEY_NAME+":"+KEY+"@dwalin-us-east-1.searchly.com";
        }
        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();

        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .multiThreaded(true)
                .build());
        jestClient = factory.getObject();
    }

    public SearchService(){

        connectionUrl = System.getenv("SEARCHBOX_URL");
        // Construct a new Jest client according to configuration via factory
        JestClientFactory factory = new JestClientFactory();

        factory.setHttpClientConfig(new HttpClientConfig
                .Builder(connectionUrl)
                .multiThreaded(true)
                .build());
        jestClient = factory.getObject();
    }

    public ToDoResponse indexAll(List<TodoInstance> todos) {

        try {
            jestClient.execute(new CreateIndex.Builder("todos").build());
            for (TodoInstance todo:todos) {
                Index index = new Index.Builder(todo).index("todos").type("todo").build();
                jestClient.execute(index);
            }


            return new ToDoResponse("ToDos indexed", true);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ToDoResponse("There was an error indexing. Please look at the logs.",false);
        }
    }

    private Collection<? extends BulkableAction> createList(List<TodoInstance> todos) {
        List<BulkableAction>bulkAction = new ArrayList<BulkableAction>();
        for (TodoInstance todo:todos) {
            bulkAction.add(new Index.Builder(todo).build());
        }
        return bulkAction;
    }

    public List<TodoInstance> searchToDoTitle(String toDoQuery) {
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            if (currentStrategy.equals(Strategy.SIMPLE)) {
                searchSourceBuilder.query(QueryBuilders.simpleQueryString(toDoQuery));
            }
            else if(currentStrategy.equals(Strategy.BOOSTING)) {
                searchSourceBuilder.query(QueryBuilders.boostingQuery()
                        .positive(QueryBuilders.termQuery("title", toDoQuery))
                        .negative(QueryBuilders.termQuery("body", toDoQuery))
                        .negativeBoost(0.1f));
            }
            else if (currentStrategy.equals(Strategy.MULTIPLE)){
                searchSourceBuilder.query(QueryBuilders.multiMatchQuery(toDoQuery, "title^3", "body"));
            }
            else {
                searchSourceBuilder.query(QueryBuilders.filteredQuery(QueryBuilders.termQuery("title",
                                toDoQuery).boost(0.2f), FilterBuilders.termFilter("body", toDoQuery)));
            }
            System.out.println("QUERY: " + searchSourceBuilder.toString());
            Search search = (Search) new Search.Builder(searchSourceBuilder.toString())
                    // multiple index or types can be added.
                    .addIndex("todos")
                    .addType("todo")
                    .build();

            JestResult result = jestClient.execute(search);
            if (result!=null){
                List<TodoInstance> todos = result.getSourceAsObjectList(TodoInstance.class);
                return todos;
            }

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ArrayList<TodoInstance>();
    }

    public List<QueryResponse> deprecatedSearchToDoTitle(String toDoQuery) {
        try {
            Node node = nodeBuilder().node();
            Client client = node.client();
           // Client client = (Client) jestClient;
            QueryBuilder multiMatch = multiMatchQuery(toDoQuery, "title^3", "body");  // <!-- I wanted to use this one!
            System.out.println(multiMatch.toString());
            SearchResponse response = client.prepareSearch("todos")
                    .setQuery(multiMatch)
                    .execute().actionGet();
            if (response.getHits().getTotalHits()>0){
                List<QueryResponse> responses = new ArrayList<QueryResponse>();
                for (SearchHit hit:response.getHits()){
                    QueryResponse qr = new QueryResponse(hit.getId(), hit.getType(), hit.getSource().toString());
                    responses.add(qr);
                }
                return responses;

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}