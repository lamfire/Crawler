package com.lamfire.crawler;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import com.lamfire.utils.HttpClient;
import com.lamfire.utils.StringUtils;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerTask implements Callable<HttpResult> {
    private static final Logger LOGGER = Logger.getLogger(CrawlerTask.class);
    private String agentUrl;
    private HttpRequest request;
    private int readTimeoutMillis = 180 * 1000;

    public CrawlerTask(String agentUrl, HttpRequest request){
        this.agentUrl = agentUrl;
        this.request = request;
    }

    public void setReadTimeoutMillis(int readTimeoutMillis) {
        this.readTimeoutMillis = readTimeoutMillis;
    }

    public HttpResult call() throws Exception {
        HttpResult result = new HttpResult();
        try{

            JSON json = JSON.fromJavaObject(request);

            LOGGER.debug("[CALL] : "+agentUrl +" => " + json);

            HttpClient client = new HttpClient();
            client.setReadTimeout(readTimeoutMillis);
            client.setMethod("post");
            client.open(agentUrl);
            client.post(json.toBytes("utf-8"));

            int status = client.getResponseCode();
            String mesg = client.getResponseMessage();

            result.setResponseStatusCode(status);
            result.setResponseMessage(mesg);


            LOGGER.debug("[STATUS] : "+agentUrl + " = " +status +" - " + mesg);

            byte[] body = null;
            if(StringUtils.isBlank(request.getCallback())){
               body = client.read();
               result.setBody(body);
            }

        }catch (Throwable t){
            String message = t.getMessage() + " => " +agentUrl+ " -> " + request.getUrl();
            result.setException(new IOException(message,t));
        }
        return result;
    }
}
