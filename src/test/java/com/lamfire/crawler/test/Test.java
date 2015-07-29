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
public class Test {
    static final String  agentUrl = "http://test-usacentral.cloudapp.net:8002/agent";

    static void  testThreadPool()throws Exception{
        ThreadPoolExecutor service = Threads.newFixedThreadPool(4);

        HttpRequest item = new HttpRequest();
        item.setMethod("GET");
        item.setUrl("https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?id=29099&popId=30&genreId=36");
        item.setHeader("User-Agent", "iTunes/12.1.1 (Windows; Microsoft Windows 7 x64 Ultimate Edition Service Pack 1 (Build 7601)) AppleWebKit/7600.1017.9000.2");
        item.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        item.setHeader("Referer", "https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewGrouping?cc=cn&id=29099&mt=8");
        item.setHeader("X-Apple-Store-Front", "143465-19,28");
        item.setHeader("X-Apple-Tz", "28800");
        item.setHeader("Accept-Encoding", "deflate");
        item.setHeader("Connection", "keep-alive");
        item.setHeader("Proxy-Connection", "keep-alive");

        CrawlerTask task = new CrawlerTask(agentUrl,item);
        Future<HttpResult> future = service.submit(task);

        HttpResult result = future.get();
        System.out.println(new String(result.getBody()));
    }

    public static void test(String[] args) throws Exception{
        HttpRequest request = new HttpRequest();
        request.setMethod("GET");
        request.setUrl("https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?id=29099&popId=30&genreId=36");
        request.setHeader("User-Agent", "iTunes/12.1.1 (Windows; Microsoft Windows 7 x64 Ultimate Edition Service Pack 1 (Build 7601)) AppleWebKit/7600.1017.9000.2");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Referer", "https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewGrouping?cc=cn&id=29099&mt=8");
        request.setHeader("X-Apple-Store-Front", "143465-19,28");
        request.setHeader("X-Apple-Tz", "28800");
        request.setHeader("Accept-Encoding", "deflate");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Proxy-Connection", "keep-alive");

        HttpClient client = new HttpClient();
        client.setReadTimeout(180 * 1000);
        client.setMethod("post");
        client.open(agentUrl);
        client.post(JSON.fromJavaObject(request).toBytes("utf-8"));

        String body = client.readAsString();
        System.out.println(body);
    }

    public static void testExecutor() throws Exception{
        HttpCrawler crawler = HttpCrawlerFactory.getHttpCrawler();

        HttpRequest request = new HttpRequest();
        request.setMethod("GET");
        request.setUrl("https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewTop?id=29099&popId=30&genreId=36");
        request.setHeader("User-Agent", "iTunes/12.1.1 (Windows; Microsoft Windows 7 x64 Ultimate Edition Service Pack 1 (Build 7601)) AppleWebKit/7600.1017.9000.2");
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Referer", "https://itunes.apple.com/WebObjects/MZStore.woa/wa/viewGrouping?cc=cn&id=29099&mt=8");
        request.setHeader("X-Apple-Store-Front", "143465-19,28");
        request.setHeader("X-Apple-Tz", "28800");
        request.setHeader("Accept-Encoding", "deflate");
        request.setHeader("Connection", "keep-alive");
        request.setHeader("Proxy-Connection", "keep-alive");

        byte[] bytes = crawler.execute(request) ;
        System.out.println(new String(bytes));

    }

    public static void main(String[] args) throws Exception{
        testExecutor();
        System.exit(0);
    }
}
