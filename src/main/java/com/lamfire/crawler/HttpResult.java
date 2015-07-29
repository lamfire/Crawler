package com.lamfire.crawler;

/**
 * Created with IntelliJ IDEA.
 * User: linfan
 * Date: 15-7-24
 * Time: 下午2:51
 * To change this template use File | Settings | File Templates.
 */
public class HttpResult {
    private byte[] body;
    private int responseStatusCode;
    private String responseMessage;
    public Exception exception;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(int responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "responseStatusCode=" + responseStatusCode +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
