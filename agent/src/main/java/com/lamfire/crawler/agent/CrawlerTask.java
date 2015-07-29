package com.lamfire.crawler.agent;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 上午11:50
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerTask implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(CrawlerTask.class);

    private JSON request;
    private byte[] responseBody;
    private boolean success = true;
    private Exception exception;

    public CrawlerTask(JSON request){
        this.request = request;
    }

    @Override
    public void run() {

        try{
            LOGGER.debug("[RUNNING] : " + request.toJSONString());
            String url = request.getString("url");

            if(StringUtils.isStartWithIgnoreCase(url, "https")){
                LOGGER.debug("[CALL HTTPS] : " + url);
                responseBody = HttpUtils.https(request);
            } else{
                LOGGER.debug("[CALL HTTP] : " + url);
                responseBody = HttpUtils.http(request);
            }

            success = true;
            LOGGER.debug("[CALL SUCCESS] : response body length = " + responseBody.length);
            String callback = request.getString("callback");
            if(StringUtils.isNotBlank(callback)){
                LOGGER.debug("[CALLBACK TO] : " + callback);
                HttpUtils.callback(callback,responseBody,request);
            }
        }catch (Exception t){
            LOGGER.error("[CALL FAILED] :" + t.getMessage(),t);
            success = false;
            exception = t;
        }
    }

    public byte[] getResponseBody() {
        return responseBody;
    }

    public boolean isSuccess() {
        return success;
    }

    public Exception getException() {
        return exception;
    }
}
