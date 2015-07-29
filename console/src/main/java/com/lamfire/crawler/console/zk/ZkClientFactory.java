package com.lamfire.crawler.console.zk;

import com.lamfire.logger.Logger;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-24
 * Time: 下午5:41
 * To change this template use File | Settings | File Templates.
 */
public class ZkClientFactory {
    private static final Logger LOGGER = Logger.getLogger(ZkClientFactory.class);
    private static String zkConnection =null;
    private static int zkConnectTimeout = 2000;
    private static int zkSessionTimeout = 15000;
    private static ZkClient zkClient;

    static {
        loadZkClientConfig();
    }

    private static void loadZkClientConfig(){
        try {
            ZkOpts opts = ZkOpts.CONFIGURED_CRAWLER_OPTS;
            zkConnection = opts.getZkConnection();
            zkConnectTimeout = opts.getZkConnectTimeout();
            zkSessionTimeout = opts.getZkSessionTimeout();

            LOGGER.info("zookeeper.connection :" + zkConnection);
            LOGGER.info("zookeeper.connection.timeout.ms :" + zkConnectTimeout);
            LOGGER.info("zookeeper.session.timeout.ms :" + zkSessionTimeout);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            LOGGER.error("Read 'node.conf' resource failed, process exited.");
            System.exit(-1);
        }
    }

    public synchronized static ZkClient getZkClient(){
        if(zkClient == null){
            zkClient = new ZkClient(zkConnection,zkSessionTimeout,zkConnectTimeout) ;
        }
        return zkClient;
    }


}
