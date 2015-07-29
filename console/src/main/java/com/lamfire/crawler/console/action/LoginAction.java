package com.lamfire.crawler.console.action;

import com.lamfire.crawler.console.zk.ZkUser;
import com.lamfire.crawler.console.zk.ZkUserMgr;
import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.action.ActionForward;
import com.lamfire.wkit.action.ServletAction;
import com.lamfire.wkit.action.TextAction;

import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class LoginAction extends ServletAction {

    private String username;
    private String password;
    private String captcha;

    private String message;

    @Override
    public ActionForward execute() {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            message = "用户名或密码未完整输入.";
            return forward("/login.jsp");
        }

        if(!CaptchaAction.validate(captcha)){
            message = "验证码输入不正确.";
            return forward("/login.jsp");
        }

        ZkUser user = ZkUserMgr.getUser();
        if(StringUtils.equals(user.getAccount(),username) && StringUtils.equals(user.getPassword(),password)){
            getSession().put("ADMIN",user);
            return redirect("/main.jsp");
        }

        message = "用户名或密码输入不正确.";
        return forward("/login.jsp");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
