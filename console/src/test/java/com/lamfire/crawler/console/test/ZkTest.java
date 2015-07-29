package com.lamfire.crawler.console.test;

import com.lamfire.crawler.console.zk.ZkClientFactory;
import org.I0Itec.zkclient.ZkClient;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-28
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class ZkTest {
    public static void main(String[] args) {
        ZkClient client = ZkClientFactory.getZkClient();
        System.out.println(client.exists("/node"));
    }
}
