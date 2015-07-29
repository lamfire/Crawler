package com.lamfire.crawler.agent;

import com.lamfire.logger.Logger;
import com.lamfire.warden.Action;
import com.lamfire.warden.ActionContext;
import com.lamfire.warden.anno.ACTION;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-22
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@ACTION(path="/check")
public class CheckAction implements Action {
    private static final Logger LOGGER = Logger.getLogger(CheckAction.class);

    @Override
    public void execute(ActionContext actionContext) {
        actionContext.sendResponseStatus(HttpResponseStatus.OK);
    }

}
