package com.lamfire.crawler;

import com.lamfire.logger.Logger;
import com.lamfire.utils.Lists;
import com.lamfire.utils.RandomUtils;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
class ZkHttpCrawler extends BaseHttpCrawler {
    private static final Logger LOGGER = Logger.getLogger(ZkHttpCrawler.class);
    private CrawlerAddressMgr addressMgr;

    public ZkHttpCrawler(CrawlerAddressMgr mgr, ThreadPoolExecutor executor){
        super(executor);
        this.addressMgr = mgr;
    }

    @Override
    String crawlerUrl() {
        List<String> agents = Lists.newArrayList(addressMgr.getCrawlerAddresses());
        int i = RandomUtils.nextInt(agents.size());
        return agents.get(i);
    }

}
