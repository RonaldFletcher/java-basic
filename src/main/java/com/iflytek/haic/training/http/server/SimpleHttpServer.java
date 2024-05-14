package com.iflytek.haic.training.http.server;


import com.iflytek.haic.training.http.server.handlers.DefaultRequestHandler;
import com.iflytek.haic.training.http.util.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * 基于Socket的
 * 简单的Http服务端
 * 与 HttpClientHandler 或其子类配套使用
 */
@SuppressWarnings({"java:S2189", "java:S2629"})
public class SimpleHttpServer {
    private static final Logger log = LoggerFactory.getLogger(SimpleHttpServer.class);

    private ExecutorService executorService;

    private final int port;

    private final List<RequestHandler> requestHandlers;

    public SimpleHttpServer(int port) {
        this.port = port;
        setupThreadPool();
        requestHandlers = new ArrayList<>();
    }


    public void startServer() throws IOException {
        // 1、创建一个 ServerSocket 实例，
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info(String.format("服务端已启动，监听 %d 端口，等待客户端连接...", port));

            for (; ; ) {
                // 2、等待客户端连接，如果没有客户端连接，会一直阻塞在这里
                // 如果有客户端连接，会返回一个 Socket 实例，用于和客户端通信
                Socket clientSocket = serverSocket.accept();

                // 3、将客户端通信交给线程池处理
                Runnable clientHandler = createSocketHandler(clientSocket);
                this.executorService.execute(clientHandler);
            }
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }


    /**
     * 添加请求处理器
     *
     * @param requestHandler 请求处理器
     */
    public void addRequestHandler(RequestHandler requestHandler) {
        this.requestHandlers.add(requestHandler);
    }


    /**
     * 创建一个用于处理请求的线程池
     */
    private synchronized void setupThreadPool() {
        if (executorService == null) {
            executorService = new ThreadPoolExecutor(
                    10,
                    10,
                    30L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(100),
                    new ThreadFactory() {
                        private final AtomicInteger threadNumber = new AtomicInteger(1);

                        @Override
                        public Thread newThread(Runnable r) {
                            return new Thread(r, "SimpleHttpServer-Thread-" + threadNumber.getAndIncrement());
                        }
                    },
                    new ThreadPoolExecutor.CallerRunsPolicy()
            );
        }
    }


    private Runnable createSocketHandler(Socket clientSocket) {
        // 解析 HTTP 协议
        return () -> {
            // 获取客户端的 IP 和端口
            String clientIp = clientSocket.getInetAddress().getHostAddress();
            int clientPort = clientSocket.getPort();
            log.info("客户端 " + clientIp + ":" + clientPort + " 已连接");

            try (// 获取输入流，用于读取客户端发送的数据
                 InputStream inputStream = clientSocket.getInputStream();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                 // 获取输出流，用于向客户端发送数据
                 OutputStream outputStream = clientSocket.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))
            ) {
                // 1、获取请求
                SimpleHttpRequest httpRequest = parseRequest(reader);

                // 2、生成响应
                SimpleHttpResponse httpResponse = generateResponse(httpRequest);

                // 写出响应
                // 1、报文首部
                // 1.1、状态行
                writer.write(httpResponse.getProtocol() + " " + httpResponse.getStatusCode() + "\r\n");
                // 1.2、响应头
                for (Map.Entry<String, String> entry : httpResponse.getHeaders().entrySet()) {
                    writer.write(entry.getKey() + ": " + entry.getValue() + "\r\n");
                }
                // 2、空行
                writer.write("\r\n");
                // 3、报文主体
                if (httpResponse.getBody() != null) {
                    writer.write(httpResponse.getBody());
                }

                writer.flush();
            } catch (IOException e) {
                log.severe("处理客户端请求失败: " + e.getMessage());
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        };
    }

    /**
     * 解析 Http 请求报文
     * <p>
     * 1、报文首部
     * 1.1、请求行
     * 1.2、请求头
     * 2、CR+LF 空行
     * 3、报文主体
     *
     * @param reader 请求输入流
     * @return SimpleHttpRequest 对象
     * @throws IOException IO异常
     */
    private SimpleHttpRequest parseRequest(BufferedReader reader) throws IOException {
        SimpleHttpRequest simpleHttpRequest = new SimpleHttpRequest();

        // 1、报文首部，请求行
        String requestLine = reader.readLine();
        String[] requestParts = requestLine.split("\\s");
        // 1.1、请求方法 GET、POST 等
        String method = requestParts[0];
        simpleHttpRequest.setMethod(method);

        // 1.2、请求 URI
        String uri = requestParts[1];
        // 对 uri 进行解码
        uri = URLDecoder.decode(uri, StandardCharsets.UTF_8.name());
        simpleHttpRequest.setUri(uri);

        // 1.2.0、请求路径和查询参数
        if (uri.contains("?")) {
            String[] uriParts = uri.split("\\?");
            // 1.2.1、请求路径
            String path = uriParts[0];
            simpleHttpRequest.setPath(path);
            // 1.2.2、查询参数
            String queryString = uriParts[1];
            String[] queryParts = queryString.split("&");
            Map<String, String> parameters = new HashMap<>();
            for (String queryPart : queryParts) {
                String[] kv = queryPart.split("=");
                parameters.put(kv[0], kv[1]);
            }
            simpleHttpRequest.setParameters(parameters);
        } else {
            simpleHttpRequest.setPath(uri);
        }

        // 1.3、协议版本
        String protocol = requestParts[2];
        simpleHttpRequest.setProtocol(protocol);

        // 1.4、打印请求行信息
        log.info("请求行：\n" + method + " " + uri + " " + protocol);

        // 2、报文首部，请求头
        // 2.1、解析请求头
        Map<String, String> headers = new HashMap<>();
        String headerLine;
        while (!(headerLine = reader.readLine()).isEmpty()) {
            String[] headerParts = headerLine.split(": ");
            String headerName = headerParts[0];
            String headerValue = headerParts[1];
            headers.put(headerName, headerValue);
        }
        simpleHttpRequest.setHeaders(headers);
        // 2.2、打印请求头信息
        StringBuilder sb = new StringBuilder("请求头：\n");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        log.info(sb.toString());


        // 3、报文主体，可能存在
        // 3.1、读取 Content-Length，如果为 0 则直接返回
        int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
        if (contentLength == 0) {
            return simpleHttpRequest;
        }

        // 3.2、解析请求体
        char[] charBuf = new char[contentLength];
        int readCharLen = reader.read(charBuf);
        String body = new String(charBuf, 0, readCharLen);
        simpleHttpRequest.setBody(body);

        // 3.3、打印请求体信息
        log.info("请求体：\n" + body);

        return simpleHttpRequest;
    }

    /**
     * 根据请求入参生成响应 Http 响应报文
     * <p>
     * 1、报文首部
     * 1.1、状态行
     * 1.2、响应头
     * 2、CR+LF 空行
     * 3、报文主体
     *
     * @param request 请求对象
     * @return 响应对象
     */
    private SimpleHttpResponse generateResponse(SimpleHttpRequest request) {
        // 1、从请求处理器列表中查找合适的处理器
        // 如果查找到能够处理请求的处理器，则调用处理器的 doHandle 方法返回响应报文
        for (RequestHandler requestHandler : this.requestHandlers) {
            if (requestHandler.canHandle(request)) {
                return requestHandler.doHandle(request);
            }
        }

        // 2、如果没有找到合适的处理器，则返回默认的 404 响应
        return new DefaultRequestHandler().doHandle(request);
    }
}
