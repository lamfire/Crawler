package com.lamfire.crawler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-22
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequest {
    private Map<String,String> headers = new HashMap<String, String>();
    private Map<String,Object> args = new HashMap<String, Object>();
    private String id;
    private String url;
    private String method;
    private String callback;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public void setArg(String key,Object val){
        this.args.put(key,val);
    }

    public void setHeader(String key,String value){
        this.headers.put(key,value);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
