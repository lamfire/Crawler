package com.lamfire.crawler.agent;

import com.lamfire.filequeue.FileQueue;
import com.lamfire.filequeue.FileQueueBuilder;
import com.lamfire.json.JSON;
import com.lamfire.logger.Logger;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public class BlockQueue {
    private static final Logger LOGGER = Logger.getLogger(BlockQueue.class);
    private FileQueue queue;

    public BlockQueue(String dataDir, String name) throws IOException {
        FileQueueBuilder builder = new FileQueueBuilder();
        builder.dataDir(dataDir);
        builder.name(name);
        this.queue = builder.build();
        LOGGER.debug("[build] :" + dataDir + " - " + name);
    }

    public synchronized void push(JSON json){
        try{
            byte[] bytes = json.toBytes("utf-8");
            queue.push(bytes);
            this.notifyAll();
            LOGGER.debug("notifyAll ...");
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public synchronized JSON pull(){
        JSON result = null;
        byte[] bytes = null;
        try{
            while(bytes == null){
                bytes = queue.pull();
                if(bytes == null){
                    try {
                        LOGGER.debug("waiting ...");
                        this.wait();
                        LOGGER.debug("wake up ...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            result = JSON.fromBytes(bytes,"utf-8");
        }catch (Throwable t){
            t.printStackTrace();
        }
        return result;
    }

    public long size(){
        if(queue == null){
            return 0;
        }
        return queue.size();
    }
}
