package com.iflytek.haic.training.http.server;

/**
 * 请求处理器接口
 */
public interface RequestHandler {
    /**
     * 是否能处理请求
     *
     * @param request 请求
     * @return 是否能处理
     */
    boolean canHandle(SimpleHttpRequest request);


    /**
     * 处理请求
     *
     * @param request 请求
     * @return 响应
     */
    SimpleHttpResponse doHandle(SimpleHttpRequest request);
}
