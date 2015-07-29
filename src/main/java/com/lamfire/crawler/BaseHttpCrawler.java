package com.lamfire.crawler;

import com.lamfire.logger.Logger;
import com.lamfire.utils.*;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
abstract class  BaseHttpCrawler implements HttpCrawler {
    private static final Logger LOGGER = Logger.getLogger(BaseHttpCrawler.class);
    private final List<String> agents = Lists.newLinkedList();
    private ThreadPoolExecutor executor;
    private int readTimeoutMillis = 180 * 1000;

    public BaseHttpCrawler(ThreadPoolExecutor executor){
        this.executor = executor;
    }

    public void setReadTimeoutMillis(int readTimeoutMillis) {
        this.readTimeoutMillis = readTimeoutMillis;
    }


    abstract String crawlerUrl();

    @Override
    public byte[] execute(final HttpRequest request)throws Exception{
        String url = crawlerUrl();
        LOGGER.debug("[use agent] : " + url);

        request.setCallback(null);
        CrawlerTask task = new CrawlerTask(url,request);
        task.setReadTimeoutMillis(readTimeoutMillis);
        Future<HttpResult> future = executor.submit(task);

        //非回调模式,直接返回数据内容
        HttpResult result =  future.get();
        LOGGER.debug("[RESULT] : " + result);

        //异常错误
        if(result.getException() != null){
            Exception e = result.getException();
            LOGGER.debug("[EXCEPTION] : " + e.getMessage(),e);
            throw result.getException();
        }

        return result.getBody();
    }

    @Override
    public void executeAsync(HttpRequest request, String callbackUrl) throws Exception {
        String url = crawlerUrl();
        LOGGER.debug("[use agent] : " + url);

        request.setCallback(callbackUrl);
        CrawlerTask task = new CrawlerTask(url,request);
        task.setReadTimeoutMillis(readTimeoutMillis);
        executor.submit(task);
    }

}
