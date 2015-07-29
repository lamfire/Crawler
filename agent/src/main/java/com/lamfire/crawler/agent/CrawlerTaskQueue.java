package com.lamfire.crawler.agent;

import com.lamfire.logger.Logger;
import com.lamfire.utils.FilenameUtils;
import com.lamfire.utils.Threads;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 上午11:36
 * To change this template use File | Settings | File Templates.
 */
public class CrawlerTaskQueue{
    private static final Logger LOGGER = Logger.getLogger(CrawlerTaskQueue.class);
    private static BlockQueue queue;

    public synchronized static BlockQueue getQueue(){
        if(queue != null){
            return queue;
        }

        String dir = System.getProperty("user.dir");
        dir = FilenameUtils.concat(dir,"data/");
        try {
            queue = new BlockQueue(dir,"CrawlerTaskQueue");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return queue;
    }
}
