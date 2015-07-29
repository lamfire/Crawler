package com.lamfire.crawler.agent;

import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 上午11:42
 * To change this template use File | Settings | File Templates.
 */
public class AsyncCrawlerTaskExecutor extends Thread{
    private static final Logger LOGGER = Logger.getLogger(AsyncCrawlerTaskExecutor.class);
    ThreadPoolExecutor service ;

    public AsyncCrawlerTaskExecutor(ThreadPoolExecutor executor){
        this.service = executor;

    }

    @Override
    public void run() {
        BlockQueue queue = CrawlerTaskQueue.getQueue();
        while (true){
            JSON request = queue.pull();
            if(request != null){
                LOGGER.debug("[Submit Async Task] : " + request);
                service.submit(new CrawlerTask(request));
            }
        }
    }
}
