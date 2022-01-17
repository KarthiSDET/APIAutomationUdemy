package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRootRequest extends RequestRootBase{
    RequestRequest request;

    public RequestRootRequest(){

    }

    public RequestRootRequest(String name, RequestRequest request){
        super(name);
        this.request = request;
    }

    public RequestRequest getRequest() {
        return request;
    }

    public void setRequest(RequestRequest request) {
        this.request = request;
    }
}
