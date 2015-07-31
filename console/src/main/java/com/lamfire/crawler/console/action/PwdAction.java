package com.lamfire.crawler.console.action;

import com.lamfire.crawler.console.zk.ZkUser;
import com.lamfire.crawler.console.zk.ZkUserMgr;
import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;
import com.lamfire.wkit.action.TextAction;


import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class PwdAction extends TextAction {
    private static final Logger LOGGER = Logger.getLogger(PwdAction.class);
    private String password;
    private String newpassword;
    private String repassword;

    @Override
    public void execute(PrintWriter printWriter) {
        LOGGER.debug(this.toString());

        if(StringUtils.isBlank(newpassword)){
            JSON js = new JSON();
            js.put("error","输入的密码不能为空白.");
            printWriter.print(js.toJSONString());
            return;
        }

        if(!StringUtils.equals(repassword,newpassword)){
            JSON js = new JSON();
            js.put("error","两次输入的密码不相同.");
            printWriter.print(js.toJSONString());
            return;
        }

        ZkUser user = ZkUserMgr.getUser();
        if(!StringUtils.equals(user.getPassword(),password)){
            JSON js = new JSON();
            js.put("error","输入的旧密码不正确.");
            printWriter.print(js.toJSONString());
            return;
        }

        ZkUserMgr.setUser(user.getAccount(),newpassword);
        JSON js = new JSON();
        js.put("result","OK");
        printWriter.print(js.toJSONString());
        return;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @Override
    public String toString() {
        return "PwdAction{" +
                "password='" + password + '\'' +
                ", newpassword='" + newpassword + '\'' +
                ", repassword='" + repassword + '\'' +
                '}';
    }
}
