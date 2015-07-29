package com.lamfire.crawler.console.zk;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Lists;
import com.lamfire.utils.Maps;
import com.lamfire.utils.Sets;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
public class ZkCrawlerNodeMgr {
    private static final Logger LOGGER = Logger.getLogger(ZkCrawlerNodeMgr.class);
    public static final String AGENTS_NODE_PATH = "/crawler/agents";

    private static ZkCrawlerNodeMgr instance ;

    public synchronized static final ZkCrawlerNodeMgr getInstance(){
        if(instance != null){
            return instance;
        }

        instance = new ZkCrawlerNodeMgr(ZkClientFactory.getZkClient());
        return instance;
    }

    private Lock lock = new ReentrantLock();
    private final Map<String,String> crawlerNodes = Maps.newConcurrentMap();
    private ZkClient zkClient;

    private ZkCrawlerNodeMgr(ZkClient zkClient){
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
                LOGGER.debug("node node changed,refreshing...");
                updateAgentAddressesFromZkServer(strings);
            }
        }) ;
    }

    private void subscribeNodeDataChanged(String path){
        zkClient.subscribeDataChanges(path,new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object o) throws Exception {
                LOGGER.debug("handleDataChange : " + path +" = " + o );
                String url = zkClient.readData(path);
                LOGGER.debug("readData : " + url );
                crawlerNodes.put(path,url);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                LOGGER.debug("handleDataDeleted : " + s);
                crawlerNodes.remove(s);
            }
        });
    }

    private void subscribeAgentNodeDataChanged(List<String> nodeNames){
        if(nodeNames == null || nodeNames.isEmpty()){
            return;
        }

        List<String> addressList = Lists.newArrayList();
        for(String path : nodeNames){
            path = AGENTS_NODE_PATH+"/"+path;
            LOGGER.debug("path : " + path);
            subscribeNodeDataChanged(path);
        }

    }

    private void updateAgentAddressesFromZkServer(List<String> nodeNames){
        if(nodeNames == null || nodeNames.isEmpty()){
            return;
        }
        lock.lock();
        crawlerNodes.clear();
        try{
            for(String name : nodeNames){
                String path = AGENTS_NODE_PATH+"/"+name;
                LOGGER.debug("path : " + path);
                String agentAddress = zkClient.readData(path,true);
                LOGGER.debug("data : " + agentAddress);
                if(agentAddress != null){
                    crawlerNodes.put(name,agentAddress);
                    LOGGER.debug("add crawler node ["+name+"] : " + agentAddress);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void saveCrawlerNode(String name,String url){
        String path = AGENTS_NODE_PATH+"/"+name;
        if(zkClient.exists(path)){
            zkClient.writeData(path,url);
        }else{
            zkClient.createPersistent(path,url);
        }
    }

    public void removeCrawlerNode(String name){
        String path = AGENTS_NODE_PATH+"/"+name;
        if(zkClient.exists(path)){
            zkClient.delete(path);
        }
    }

    private void initAndSubscriptAgentAddressesFromZkServer(){
        List<String> names = zkClient.getChildren(AGENTS_NODE_PATH);
        updateAgentAddressesFromZkServer(names);
        subscribeAgentNodeDataChanged(names);
    }

    public Collection<String> getCrawlerAddresses() {
        lock.lock();
        try{
            return crawlerNodes.values();
        }finally{
            lock.unlock();
        }
    }

    public void flush(){
        lock.lock();
        try{
            List<String> names = zkClient.getChildren(AGENTS_NODE_PATH);
            updateAgentAddressesFromZkServer(names);
        }finally{
            lock.unlock();
        }
    }

    public Set<String> getNodeNames(){
        lock.lock();
        try{
            return crawlerNodes.keySet();
        }finally{
            lock.unlock();
        }
    }

    public Map<String,String> getAllCrawlerNodes(){
        lock.lock();
        try{
            return this.crawlerNodes;
        }finally{
            lock.unlock();
        }
    }

    public String getNodeUrl(String name){
        lock.lock();
        try{
            return this.crawlerNodes.get(name);
        }finally{
            lock.unlock();
        }
    }

}
