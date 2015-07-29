package com.lamfire.crawler.test;

import com.lamfire.crawler.*;
import com.lamfire.json.JSON;
import com.lamfire.utils.HttpClient;
import com.lamfire.utils.Threads;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-22
 * Time: 下午5:28
 * To change this template use File | Settings | File Templates.
 */
public class TestAgent {
    static final String  agentUrl = "http://127.0.0.1:8080/agent";

    static void  testAgent()throws Exception{
        HttpRequest request = new HttpRequest();
        request.setMethod("GET");
        request.setUrl("http://www.mob.com");
        request.setHeader("User-Agent", "iTunes/12.1.1 (Windows; Microsoft Windows 7 x64 Ultimate Edition Service Pack 1 (Build 7601)) AppleWebKit/7600.1017.9000.2");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Referer", "https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewGrouping?cc=cn&id=29099&mt=8");
        request.setHeader("X-Apple-Store-Front", "143465-19,28");
        request.setHeader("X-Apple-Tz", "28800");
        request.setHeader("Accept-Encoding", "deflate");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Proxy-Connection", "keep-alive");

        request.setCallback("http://127.0.0.1:8080/callback");

        CrawlerTask task = new CrawlerTask(agentUrl,request);
        HttpResult result = task.call();
        if(result.getBody() != null)
        System.out.println(new String(result.getBody()));
    }

    public static void main(String[] args) throws Exception{
        testAgent();
        System.exit(0);
    }
}
