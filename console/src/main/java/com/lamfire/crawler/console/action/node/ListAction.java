package com.lamfire.crawler.console.action.node;

import com.lamfire.crawler.console.zk.ZkCrawlerNodeMgr;
import com.lamfire.json.JSON;
import com.lamfire.wkit.action.TextAction;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
public class ListAction extends TextAction {

    @Override
    public void execute(PrintWriter printWriter) {
        Map<String,String> nodes = ZkCrawlerNodeMgr.getInstance().getAllCrawlerNodes();
        JSON json = new JSON();
        json.put("result",nodes);
        printWriter.print(json.toJSONString());
    }
}
