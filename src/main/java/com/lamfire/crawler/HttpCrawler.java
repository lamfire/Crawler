package com.lamfire.crawler;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-23
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public interface HttpCrawler {

    public byte[] execute(HttpRequest request)throws Exception;

    public void executeAsync(HttpRequest request,String callbackUrl)throws Exception;

}
