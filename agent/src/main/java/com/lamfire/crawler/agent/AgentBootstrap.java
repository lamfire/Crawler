package com.lamfire.crawler.agent;

import com.lamfire.logger.Logger;
import com.lamfire.utils.StringUtils;
import com.lamfire.utils.Threads;
import com.lamfire.warden.ActionRegistry;
import com.lamfire.warden.HttpServer;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-22
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class AgentBootstrap {
    private static final Logger LOGGER = Logger.getLogger(AgentBootstrap.class);

    public static void main(String[] args) throws Exception{
        String host ="0.0.0.0";
        int port = 8080;

        if(args != null){
            for(String arg : args){
                if(StringUtils.contains(arg, "-p")){
                    port = Integer.valueOf(StringUtils.substringAfter(arg,"-p"));
                }

                if(StringUtils.contains(arg,"-h")){
                    host = (StringUtils.substringAfter(arg, "-h"));
                }
            }
        }

        //
        ActionRegistry reg = new ActionRegistry();
        reg.mappingPackage(AgentBootstrap.class.getPackage().getName());

        HttpServer server = new HttpServer(host,port);
        server.setWorkThreads(8);
        server.startup(reg);

        //
        ThreadPoolExecutor threadPoolExecutor = Threads.newFixedThreadPool(32);
        AsyncCrawlerTaskExecutor executor = new AsyncCrawlerTaskExecutor(threadPoolExecutor);
        executor.start();

        //
        Threads.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                BlockQueue queue = CrawlerTaskQueue.getQueue();
                LOGGER.debug("Crawler task queue size = " + queue.size());
            }
        },5,5, TimeUnit.SECONDS);
    }
}
