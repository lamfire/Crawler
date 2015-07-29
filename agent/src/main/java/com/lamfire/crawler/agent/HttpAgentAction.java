package com.lamfire.crawler.agent;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import com.lamfire.utils.ArrayUtils;
import com.lamfire.utils.HttpClient;
import com.lamfire.utils.HttpsClient;
import com.lamfire.utils.StringUtils;
import com.lamfire.warden.Action;
import com.lamfire.warden.ActionContext;
import com.lamfire.warden.anno.ACTION;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-22
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@ACTION(path="/agent")
public class HttpAgentAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(HttpAgentAction.class);

    @Override
    public void execute(ActionContext actionContext) {
        byte[] body = actionContext.getHttpRequestContentAsBytes();
        if(ArrayUtils.isEmpty(body)){
            LOGGER.debug("empty body");
            actionContext.sendResponseStatus(HttpResponseStatus.BAD_REQUEST);
            return;
        }

        JSON json = null;
        try{
            String content = new String(body, Charset.forName("utf-8")) ;
            LOGGER.debug(content);

            json = JSON.fromJSONString(content);

            //异步
            String callback = json.getString("callback");
            if(StringUtils.isNotBlank(callback)){
                LOGGER.debug("push async task queue, and wait callback -> " + callback);
                CrawlerTaskQueue.getQueue().push(json);
                actionContext.sendResponseStatus(HttpResponseStatus.OK);
                return;
            }

            CrawlerTask task = new CrawlerTask(json);
            task.run();
            if(task.isSuccess()){
                actionContext.writeResponse(task.getResponseBody());
            }else{
                actionContext.sendResponseStatus(HttpResponseStatus.BAD_REQUEST);
                task.getException().printStackTrace(new PrintWriter(actionContext.getHttpResponseWriter()));
            }
        }catch (Throwable t){
            actionContext.sendResponseStatus(HttpResponseStatus.BAD_REQUEST);
            LOGGER.error(t.getMessage(),t);
        }
    }

}
