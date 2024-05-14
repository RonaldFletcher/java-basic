package com.iflytek.haic.training.http.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的 HTTP 请求客户端工具
 * 仅提供一个 post 方法用于发送 POST 请求
 */
public class SimpleHttpClient {
    private SimpleHttpClient() {
        // 私有构造方法，防止实例化
    }

    /**
     * 简单 POST 请求封装
     *
     * @param urlStr      请求地址
     * @param requestData 请求数据
     * @param headers     请求头
     * @return {code: 响应码, body: 响应体}
     * @throws IOException
     */
    public static Map<String, Object> post(String urlStr, String requestData, Map<String, String> headers) throws IOException {
        if (urlStr == null || urlStr.isEmpty()) {
            throw new IllegalArgumentException("urlStr 不能为空");
        }
        if (requestData == null || requestData.isEmpty()) {
            throw new IllegalArgumentException("requestData 不能为空");
        }

        // 记录响应数据 {code: 响应码, body: 响应体}
        Map<String, Object> responseMap = new HashMap<>();
        Integer responseCode = null;
        String responseBody = null;

        // 在 try 代码块外面定义 HttpURLConnection 对象，以便在 finally 代码块中关闭连接
        HttpURLConnection connection = null;
        try {
            // 1、创建 URL 对象
            URL url = new URL(urlStr);

            // 2、打开连接，获取到 HttpURLConnection 对象
            connection = (HttpURLConnection) url.openConnection();
            // 2.1、设置连接配置：不使用缓存 (可选)
            connection.setUseCaches(false);
            // 2.2、设置连接超时时间：5秒 (可选)
            connection.setConnectTimeout(5000);
            // 2.3、设置请求方法：POST (必填，默认为 GET)
            connection.setRequestMethod("POST");
            // 2.4、设置是否向连接输出，因为这个是 post 请求，需要发送请求体，因此需要设为 true
            connection.setDoOutput(true);

            // 3、设置请求头
            // connection.setRequestProperty("Content-Type", "application/json");
            // connection.setRequestProperty("Accept", "application/json");
            // connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
            // 如果传入请求头则添加上
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String name = entry.getKey();
                    String value = entry.getValue();
                    connection.setRequestProperty(name, value);
                }
            }

            // 4、发送请求数据
            // 4.1、从连接中获取输出流
            try (OutputStream outputStream = connection.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8))) {
                // 4.2、向输出流写入请求数据
                writer.write(requestData);
                writer.flush();
            }


            // 5、获取响应
            // 5.1、获取响应头
            System.out.println("响应头列表:");
            Map<String, List<String>> map = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                String name = entry.getKey();
                name = name == null ? "" : name;
                List<String> value = entry.getValue();
                System.out.println(name + ": " + value);
            }

            // 5.2、获取响应码
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 5.3、当响应码为 200 时，获取响应体
                try (InputStream inputStream = connection.getInputStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    responseBody = response.toString();
                }
            } else {
                // 5.5、当响应码不为 200 时，获取异常信息
                System.err.println("请求发生异常：" + responseCode);
                try (InputStream errorStream = connection.getErrorStream();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    responseBody = response.toString();
                }
            }
        } finally {
            // 6、关闭连接
            if (connection != null) {
                connection.disconnect();
            }
        }

        responseMap.put("code", responseCode);
        responseMap.put("body", responseBody);
        return responseMap;
    }
}
