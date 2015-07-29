package com.lamfire.crawler.console.action.node;

import com.lamfire.crawler.console.zk.ZkCrawlerNodeMgr;
import com.lamfire.json.JSON;
import com.lamfire.utils.StringUtils;
import com.lamfire.utils.URLUtils;
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
public class AddAction extends TextAction {

    private String nodename;
    private String nodeurl;

    @Override
    public void execute(PrintWriter printWriter) {
        if(StringUtils.isBlank(nodename)){
            JSON json = new JSON();
            json.put("error","need arg 'Name'.");
            printWriter.print(json.toJSONString());
            return;
        }

        if(StringUtils.isBlank(nodeurl)){
            JSON json = new JSON();
            json.put("error","need arg 'URL'.");
            printWriter.print(json.toJSONString());
            return;
        }

        if(!URLUtils.isUrl(nodeurl)){
            JSON json = new JSON();
            json.put("error","validate arg 'URL'.");
            printWriter.print(json.toJSONString());
            return;
        }

        ZkCrawlerNodeMgr.getInstance().saveCrawlerNode(nodename,nodeurl);
        ZkCrawlerNodeMgr.getInstance().flush();

        JSON json = new JSON();
        json.put("result","OK");
        printWriter.print(json.toJSONString());
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public String getNodeurl() {
        return nodeurl;
    }

    public void setNodeurl(String nodeurl) {
        this.nodeurl = nodeurl;
    }
}
