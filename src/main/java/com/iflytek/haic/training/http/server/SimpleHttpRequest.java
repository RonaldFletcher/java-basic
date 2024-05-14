package com.iflytek.haic.training.http.server;

import java.util.Map;

/**
 * 请求体封装
 */
public class SimpleHttpRequest {
    /**
     * 请求方法，GET/POST等
     */
    private String method;

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 请求路径
     */
    private String path;

    /**
     * Http协议
     */
    private String protocol;

    /**
     * 查询参数
     */
    private Map<String, String> parameters;

    /**
     * 请求头
     */
    private Map<String, String> headers;

    /**
     * 请求体
     */
    private String body;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
