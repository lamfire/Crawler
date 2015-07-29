package com.lamfire.crawler.agent;

import com.lamfire.json.JSON;
import com.lamfire.utils.HttpClient;
import com.lamfire.utils.HttpsClient;
import com.lamfire.utils.StringUtils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 上午11:45
 * To change this template use File | Settings | File Templates.
 */
public class HttpUtils {

    public static byte[] http(JSON json)throws Exception{
        String url = json.getString("url");
        String method = json.getString("method");
        JSON headers = json.getJSONObject("headers");
        JSON args = json.getJSONObject("args");

        HttpClient httpClient = new HttpClient();
        httpClient.setMethod(method);
        httpClient.setReadTimeout(180 * 1000);

        if(headers != null){
            for(Map.Entry<String,Object> e : headers.entrySet()){
                httpClient.setRequestHeader(e.getKey(),e.getValue().toString());
            }
        }

        httpClient.open(url);

        if(args != null && StringUtils.equalsIgnoreCase("post", method)){
            for(Map.Entry<String,Object> e : args.entrySet()){
                httpClient.addPostParameter(e.getKey(),e.getValue().toString());
            }
            httpClient.post();
        }

        byte[] resultBytes =  (httpClient.read());

        return resultBytes;
    }

    public static byte[] https(JSON json)throws Exception{
        String url = json.getString("url");
        String method = json.getString("method");
        JSON headers = json.getJSONObject("headers");
        JSON args = json.getJSONObject("args");

        HttpsClient httpClient = new HttpsClient();
        httpClient.setMethod(method);
        httpClient.setReadTimeout(180 * 1000);

        if(headers != null){
            for(Map.Entry<String,Object> e : headers.entrySet()){
                httpClient.setRequestHeader(e.getKey(),e.getValue().toString());
            }
        }

        httpClient.open(url);

        if(args != null && StringUtils.equalsIgnoreCase("post",method)){
            for(Map.Entry<String,Object> e : args.entrySet()){
                httpClient.addPostParameter(e.getKey(),e.getValue().toString());
            }
            httpClient.post();
        }

        return (httpClient.read());
    }

    public static int callback(String url,byte[] body,JSON request){
        try{
            HttpClient client = new HttpClient();
            client.setReadTimeout(180 * 1000);
            client.setRequestHeader("CLIENT_TASK",request.toJSONString());
            client.setMethod("post");
            client.open(url);
            client.post(body);
            return client.getResponseCode();
        }catch (Throwable t){
            t.printStackTrace();
            return 400;
        }
    }
}
