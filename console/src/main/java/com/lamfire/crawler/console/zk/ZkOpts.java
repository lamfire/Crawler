package com.lamfire.crawler.console.zk;

import com.lamfire.logger.Logger;
import com.lamfire.utils.PropertiesUtils;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-27
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class ZkOpts {
    private static final Logger LOGGER = Logger.getLogger(ZkClientFactory.class);
    private static final String CRAWLER_CONF = "zk.conf";
    public static final ZkOpts CONFIGURED_CRAWLER_OPTS  =  loadByConfigFile();

    private String zkConnection =null;
    private int zkConnectTimeout = 2000;
    private int zkSessionTimeout = 15000;

    private static ZkOpts loadByConfigFile(){
        try {
            Properties prop = PropertiesUtils.load(CRAWLER_CONF, ZkClientFactory.class);
            int workThreads =Integer.valueOf(prop.getProperty("work.threads","16"));
            int httpReadTimeout =Integer.valueOf(prop.getProperty("http.read.timeout.ms","180000"));

            String zkConnection = prop.getProperty("zookeeper.connection");
            int zkConnectTimeout = Integer.valueOf(prop.getProperty("zookeeper.connection.timeout.ms","2000"));
            int zkSessionTimeout = Integer.valueOf(prop.getProperty("zookeeper.session.timeout.ms","15000"));

            LOGGER.info("work.threads :" + workThreads);
            LOGGER.info("http.read.timeout.ms :" + httpReadTimeout);
            LOGGER.info("zookeeper.connection :" + zkConnection);
            LOGGER.info("zookeeper.connection.timeout.ms :" + zkConnectTimeout);
            LOGGER.info("zookeeper.session.timeout.ms :" + zkSessionTimeout);

            ZkOpts opts = new ZkOpts();
            opts.setZkConnection(zkConnection);
            opts.setZkConnectTimeout(zkConnectTimeout);
            opts.setZkSessionTimeout(zkSessionTimeout);
            return opts;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e);
            LOGGER.error("Read 'node.conf' resource failed, process exited.");
        }
        return null;
    }

    public String getZkConnection() {
        return zkConnection;
    }

    public void setZkConnection(String zkConnection) {
        this.zkConnection = zkConnection;
    }

    public int getZkConnectTimeout() {
        return zkConnectTimeout;
    }

    public void setZkConnectTimeout(int zkConnectTimeout) {
        this.zkConnectTimeout = zkConnectTimeout;
    }

    public int getZkSessionTimeout() {
        return zkSessionTimeout;
    }

    public void setZkSessionTimeout(int zkSessionTimeout) {
        this.zkSessionTimeout = zkSessionTimeout;
    }
}
