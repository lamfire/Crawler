package com.lamfire.crawler.console.action.node;

import com.lamfire.crawler.console.CrawlerNodeCheckUtils;
import com.lamfire.crawler.console.zk.ZkCrawlerNodeMgr;
import com.lamfire.json.JSON;
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
public class CheckAction extends TextAction {

    private String name;

    @Override
    public void execute(PrintWriter printWriter) {
        if(StringUtils.isBlank(name)){
            JSON json = new JSON();
            json.put("error","need arg 'Name'.");
            printWriter.print(json.toJSONString());
            return;
        }

        String nodeUrl = ZkCrawlerNodeMgr.getInstance().getNodeUrl(name);

        long startAt = System.currentTimeMillis();
        boolean avaliable = CrawlerNodeCheckUtils.isAvaliableCrawler(nodeUrl);
        long timeMillis = System.currentTimeMillis() - startAt;

        JSON json = new JSON();
        if(avaliable){
            json.put("result","OK");
            json.put("speed",timeMillis + "ms");
            json.put("status","OK");
        }else{
            json.put("result","error");
            json.put("speed",timeMillis + "ms");
            json.put("status","Failed");
        }
        printWriter.print(json.toJSONString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
