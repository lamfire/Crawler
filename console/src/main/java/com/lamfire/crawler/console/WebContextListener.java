package com.lamfire.crawler.console;

import com.lamfire.crawler.console.zk.ZkClientFactory;
import com.lamfire.crawler.console.zk.ZkUser;
import com.lamfire.crawler.console.zk.ZkUserMgr;
import com.lamfire.logger.Logger;
import org.I0Itec.zkclient.ZkClient;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-29
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */
public class WebContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(WebContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ZkUser user = ZkUserMgr.getUser();
        if(user == null){
            ZkUserMgr.setUser("admin","admin");
            LOGGER.info("[Create Admin User] :  admin/admin");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
