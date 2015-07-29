package com.lamfire.crawler.test;


import com.lamfire.crawler.ZkCrawlerAddressMgr;
import com.lamfire.crawler.ZkClientFactory;
import com.lamfire.logger.Logger;
import com.lamfire.utils.Lists;
import com.lamfire.utils.PropertiesUtils;
import com.lamfire.utils.StringUtils;
import org.I0Itec.zkclient.ZkClient;

import java.net.InetAddress;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class ZkAgentNodeLoader {
    private static final Logger LOGGER = Logger.getLogger(ZkAgentNodeLoader.class);

    public synchronized static void clear(){
        ZkClient zkClient = ZkClientFactory.getZkClient();
        List<String> subs = zkClient.getChildren(ZkCrawlerAddressMgr.AGENTS_NODE_PATH);
        for(String nodeName : subs){
            String path = ZkCrawlerAddressMgr.AGENTS_NODE_PATH+"/" + nodeName;
            zkClient.delete(path);
        }
    }

    public synchronized static void loadFormConfig(){
        //create parent
        ZkClient zkClient = ZkClientFactory.getZkClient();
        if(!zkClient.exists("/node")){
            zkClient.createPersistent("/node");
        }
        if(!zkClient.exists(ZkCrawlerAddressMgr.AGENTS_NODE_PATH)){
            zkClient.createPersistent(ZkCrawlerAddressMgr.AGENTS_NODE_PATH);
        }

        Properties prop = PropertiesUtils.load("agents.conf",ZkAgentNodeLoader.class);
        int threads = Integer.valueOf(prop.getProperty("threads", "32"));
        LOGGER.info("[threads] : " + threads);
        int timeout = Integer.valueOf(prop.getProperty("read.timeout.millis", "180000"));
        LOGGER.info("[read.timeout.millis] : " + timeout);
        List<String> agents = Lists.newArrayList();
        for(Map.Entry<Object,Object> e : prop.entrySet()){
            String key = e.getKey().toString();
            String value = e.getValue().toString();
            if(StringUtils.isStartWith(key,"agent.")){
                createZkNodeWithIPAddressUrl(value);
            }
        }
    }

    private static void createZkNodeWithIPAddressUrl(String urlAddr) {
        try{
            URL url = new URL(urlAddr);
            InetAddress addr = InetAddress.getByName(url.getHost());
            String ipAddr =  addr.getHostAddress();
            String ipAddressUrl =  StringUtils.replace(urlAddr,url.getHost(),ipAddr);
            LOGGER.info("[found agent] : " + urlAddr +" -> " +ipAddressUrl);
            ZkClient zkClient = ZkClientFactory.getZkClient();

            String path = ZkCrawlerAddressMgr.AGENTS_NODE_PATH+"/" + url.getHost();
            if(!zkClient.exists(path)){
                zkClient.createPersistent(path,ipAddressUrl);
            }else{
                zkClient.writeData(path,ipAddressUrl);
            }

        }catch (Exception e){
            LOGGER.error("[host name to host address failed] : " + e.getMessage());
        }
    }

    public static void printZkAgentNod() {
        ZkCrawlerAddressMgr mgr = new ZkCrawlerAddressMgr(ZkClientFactory.getZkClient());
        Collection<String> addresses = mgr.getCrawlerAddresses();
        for(String addr : addresses){
            System.out.println(addr);
        }
    }

    public static void main(String[] args) {
        loadFormConfig();
        //printZkAgentNod();
        //clear();
    }
}
