package com.lamfire.crawler.console.action;

import com.lamfire.wkit.action.CaptchaAction;
import com.lamfire.wkit.action.TextAction;

import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class TestAction extends TextAction {

    @Override
    public void execute(PrintWriter printWriter) {
        printWriter.print(System.currentTimeMillis());
    }
}
