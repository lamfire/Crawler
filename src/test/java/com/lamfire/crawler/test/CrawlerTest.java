package com.lamfire.crawler.test;

import com.lamfire.crawler.HttpCrawler;
import com.lamfire.crawler.HttpCrawlerFactory;
import com.lamfire.crawler.HttpRequest;
import com.lamfire.utils.Threads;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerTest {

    public static void main(String[] args)throws Exception{
        HttpCrawler executor = HttpCrawlerFactory.getHttpCrawler();
        HttpRequest request = new HttpRequest();
        request.setMethod("GET");
        request.setUrl("http://www.mob.com");
        byte[] bytes = executor.execute(request) ;
        if(bytes != null){
            System.out.println(new String(bytes));
        }
        Threads.sleep(1000);
        System.exit(0);
    }


}
