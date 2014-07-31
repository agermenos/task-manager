package com.tasks.model;

/**
 * Created by Alejandro on 7/30/2014.
 */
public class QueryResponse {
    private String id;
    private String type;
    private String source;

    public QueryResponse(String id, String type, String source) {
        this.id = id;
        this.type = type;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "QueryResponse{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
