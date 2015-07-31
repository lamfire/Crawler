package com.lamfire.crawler.console.action;

import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ServletAction;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class LogoutAction extends ServletAction {
    @Override
    public ActionForward execute() {
        getSession().put("ADMIN",null);
        getSession().remove("ADMIN");
        return redirect("/login.jsp");
    }
}
