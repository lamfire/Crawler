package com.lamfire.crawler.console.zk;

import com.lamfire.json.JSON;
import org.I0Itec.zkclient.ZkClient;

import java.nio.file.attribute.UserDefinedFileAttributeView;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-29
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
public class ZkUserMgr {

    public static void setUser(String account ,String password){
        ZkClient client = ZkClientFactory.getZkClient();
        String root  = "/crawler";
        ZkUser user = new ZkUser();
        user.setAccount(account);
        user.setPassword(password);
        JSON json = JSON.fromJavaObject(user);
        if(!client.exists(root)){
            client.createPersistent(root,json.toJSONString());
            return;
        }
        client.writeData(root,json.toJSONString());
    }

    public static ZkUser getUser(){
        ZkClient client = ZkClientFactory.getZkClient();
        String root  = "/crawler";
        if(!client.exists(root)){
            return null;
        }

        String json = client.readData(root);
        return JSON.toJavaObject(json,ZkUser.class);
    }
}
