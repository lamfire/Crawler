package com.lamfire.crawler.console.action.node;

import com.lamfire.crawler.console.zk.ZkCrawlerNodeMgr;
import com.lamfire.json.JSON;
import com.lamfire.utils.StringUtils;
import com.lamfire.utils.URLUtils;
import com.lamfire.wkit.action.TextAction;

import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class RemoveAction extends TextAction {

    private String name;

    @Override
    public void execute(PrintWriter printWriter) {
        if(StringUtils.isBlank(name)){
            JSON json = new JSON();
            json.put("error","need arg 'Name'.");
            printWriter.print(json.toJSONString());
            return;
        }

        ZkCrawlerNodeMgr.getInstance().removeCrawlerNode(name);
        ZkCrawlerNodeMgr.getInstance().flush();

        JSON json = new JSON();
        json.put("result","OK");
        printWriter.print(json.toJSONString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
