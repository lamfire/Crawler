package com.lamfire.crawler;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Lists;
import com.lamfire.utils.Sets;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-24
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
public class ZkCrawlerAddressMgr implements CrawlerAddressMgr {
    private static final Logger LOGGER = Logger.getLogger(ZkCrawlerAddressMgr.class);
    public static final String AGENTS_NODE_PATH = "/crawler/agents";

    private Lock lock = new ReentrantLock();
    private final Set<String> agentAddresses = Sets.newHashSet();
    private ZkClient zkClient;

    public ZkCrawlerAddressMgr(ZkClient zkClient){
        this.zkClient = zkClient;
        init();
    }

    private void init(){
        initAndSubscriptAgentAddressesFromZkServer();
        subscribeAgentNodeChanged();
    }

    private void subscribeAgentNodeChanged(){
        zkClient.subscribeChildChanges(AGENTS_NODE_PATH,new IZkChildListener() {
            @Override
            public void handleChildChange(String s, List<String> strings) throws Exception {
                LOGGER.debug("agent node changed,refreshing...");
                updateAgentAddressesFromZkServer(strings);
            }
        }) ;
    }

    private void subscribeAgentNodeDataChanged(String path){
        zkClient.subscribeDataChanges(path,new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                LOGGER.debug("handleDataDeleted : " + s + "=" +o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                LOGGER.debug("handleDataDeleted : " + s);
            }
        });
    }

    private void subscribeAgentNodeDataChanged(List<String> agents){
        if(agents == null || agents.isEmpty()){
            return;
        }

        List<String> addressList = Lists.newArrayList();
        for(String path : agents){
            path = AGENTS_NODE_PATH+"/"+path;
            LOGGER.debug("path : " + path);
            subscribeAgentNodeDataChanged(path);
        }

    }

    private void updateAgentAddressesFromZkServer(List<String> paths){
        if(paths == null || paths.isEmpty()){
            return;
        }
        lock.lock();
        try{
            List<String> addressList = Lists.newArrayList();
            for(String path : paths){
                path = AGENTS_NODE_PATH+"/"+path;
                LOGGER.debug("path : " + path);
                String agentAddress = zkClient.readData(path,true);
                if(agentAddress != null){
                    addressList.add(agentAddress);
                    LOGGER.debug("add agent node : " + agentAddress);
                }
            }
            agentAddresses.clear();
            agentAddresses.addAll(addressList);
        }finally {
            lock.unlock();
        }
    }

    private void initAndSubscriptAgentAddressesFromZkServer(){
        List<String> paths = zkClient.getChildren(AGENTS_NODE_PATH);
        updateAgentAddressesFromZkServer(paths);
        subscribeAgentNodeDataChanged(paths);
    }

    @Override
    public Collection<String> getCrawlerAddresses() {
        lock.lock();
        try{
            return agentAddresses;
        }finally{
            lock.unlock();
        }
    }


}
