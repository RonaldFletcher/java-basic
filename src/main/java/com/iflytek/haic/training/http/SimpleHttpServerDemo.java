package com.iflytek.haic.training.http;

import com.iflytek.haic.training.http.server.SimpleHttpServer;
import com.iflytek.haic.training.http.server.handlers.MedicalInfoGetHandler;

import java.io.IOException;

/**
 * SimpleHttpServer 使用示例
 */
public class SimpleHttpServerDemo {
    public static void main(String[] args) throws IOException {
        // 1、创建一个监听 18080 端口的服务器
        SimpleHttpServer httpServer = new SimpleHttpServer(18080);

        // 2、添加用于处理各种请求的处理器
        // 2.1、添加处理 /medical-info 路径的 GET 请求的处理器
        httpServer.addRequestHandler(new MedicalInfoGetHandler());
        // 2.2、添加其它处理器 ......

        // 3、启动服务端
        httpServer.startServer();
    }
}
