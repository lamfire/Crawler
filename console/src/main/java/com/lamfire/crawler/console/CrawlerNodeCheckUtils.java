package com.lamfire.crawler.console;

import com.lamfire.utils.HttpClient;
import com.lamfire.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午6:24
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerNodeCheckUtils {
    public static boolean isAvaliableCrawler(String nodeUrl){
        HttpClient client = new HttpClient();
        String checkUrl = StringUtils.replace(nodeUrl,"agent","check");
        client.setMethod("GET");

        try{
        client.open(checkUrl);
        client.read();

        if(client.getResponseCode() == 200){
            return true;
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(CrawlerNodeCheckUtils.isAvaliableCrawler("http://test-usacentral.cloudapp.net:8002/agent"));
    }
}
