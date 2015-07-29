package com.lamfire.crawler.agent;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import com.lamfire.utils.ArrayUtils;
import com.lamfire.utils.StringUtils;
import com.lamfire.warden.Action;
import com.lamfire.warden.ActionContext;
import com.lamfire.warden.anno.ACTION;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-22
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@ACTION(path="/callback")
public class CallbackTestAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(CallbackTestAction.class);

    @Override
    public void execute(ActionContext actionContext) {
        byte[] body = actionContext.getHttpRequestContentAsBytes();
        if(ArrayUtils.isEmpty(body)){
            LOGGER.debug("empty body");
            actionContext.sendResponseStatus(HttpResponseStatus.BAD_REQUEST);
            return;
        }

        String content = new String(body, Charset.forName("utf-8")) ;
        LOGGER.debug(content);
        actionContext.sendResponseStatus(HttpResponseStatus.OK);
    }

}
