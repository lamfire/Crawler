package com.lamfire.crawler;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Threads;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class HttpCrawlerFactory {
    private static final Logger LOGGER = Logger.getLogger(HttpCrawlerFactory.class);
    private static HttpCrawler crawler;

    public synchronized static HttpCrawler getHttpCrawler(){
        if(crawler == null){
            crawler = make();
        }
        return crawler;
    }

    private synchronized static HttpCrawler make(){
        CrawlerOpts opts = CrawlerOpts.CONFIGURED_CRAWLER_OPTS;
        ZkCrawlerAddressMgr mgr = new ZkCrawlerAddressMgr(ZkClientFactory.getZkClient());
        ZkHttpCrawler she = new ZkHttpCrawler(mgr,Threads.newFixedThreadPool(opts.getWorkThreads()));
        she.setReadTimeoutMillis(opts.getHttpReadTimeout());
        return she;
    }
}
