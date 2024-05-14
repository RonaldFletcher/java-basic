package com.iflytek.haic.training.http.server.handlers;

import com.iflytek.haic.training.http.server.RequestHandler;
import com.iflytek.haic.training.http.server.SimpleHttpRequest;
import com.iflytek.haic.training.http.server.SimpleHttpResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认请求处理器实现类
 * 将返回404
 */
public class DefaultRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(SimpleHttpRequest request) {
        return true;
    }

    @Override
    public SimpleHttpResponse doHandle(SimpleHttpRequest request) {
        SimpleHttpResponse simpleHttpResponse = new SimpleHttpResponse();
        // 1、报文首部
        // 1.1、状态行
        // 1.1.1、协议版本
        simpleHttpResponse.setProtocol(request.getProtocol());
        // 1.1.2、状态码
        simpleHttpResponse.setStatusCode(404);

        // 1.2、响应头
        // 1.2.1、响应头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Server", "SimpleHttpServer");
        simpleHttpResponse.setHeaders(headers);

        // 3、报文主体
        // 3.1、响应体
        StringBuilder sb = new StringBuilder("{\n");
        sb.append("  \"code\": 404,\n");
        sb.append("  \"error\": \"Not Found\",\n");
        sb.append("  \"message\": \"\",\n");
        sb.append("  \"timestamp\": ").append(System.currentTimeMillis()).append(",\n");
        sb.append("  \"path\": \"").append(request.getPath()).append("\"\n");
        sb.append("}");
        String responseBody = sb.toString();
        simpleHttpResponse.setBody(responseBody);

        // 3.2、更新 Content-Length
        headers.put("Content-Length", String.valueOf(responseBody.getBytes(StandardCharsets.UTF_8).length));

        return simpleHttpResponse;
    }
}
