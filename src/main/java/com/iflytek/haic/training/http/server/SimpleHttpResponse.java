package com.iflytek.haic.training.http.server;

import java.util.Map;

/**
 * Http 响应体封装
 */
public class SimpleHttpResponse {
    /**
     * Http协议
     */
    private String protocol;

    /**
     * 状态码
     */
    private int statusCode;

    /**
     * 响应头
     */
    private Map<String, String> headers;

    /**
     * 响应体
     */
    private String body;


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
