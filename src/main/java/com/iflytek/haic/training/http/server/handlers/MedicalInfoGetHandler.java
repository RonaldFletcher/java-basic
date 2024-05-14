package com.iflytek.haic.training.http.server.handlers;

import com.alibaba.fastjson.JSON;
import com.iflytek.haic.training.http.dto.medical.MedicalRecordDTO;
import com.iflytek.haic.training.http.server.RequestHandler;
import com.iflytek.haic.training.http.server.SimpleHttpRequest;
import com.iflytek.haic.training.http.server.SimpleHttpResponse;
import com.iflytek.haic.training.http.util.ReadFileUtil;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * /medical-info 路径的请求处理器
 * 只处理 Get 请求，
 * 接收到请求之后，
 * 从 resources/medicalArray.json.json 文件中随机读取一条数据返回
 */
public class MedicalInfoGetHandler implements RequestHandler {
    @Override
    public boolean canHandle(SimpleHttpRequest request) {
        return "/medical-info".equals(request.getPath()) && "GET".equals(request.getMethod());
    }

    @Override
    public SimpleHttpResponse doHandle(SimpleHttpRequest request) {
        SimpleHttpResponse simpleHttpResponse = new SimpleHttpResponse();
        // 1、报文首部
        // 1.1、状态行
        // 1.1.1、协议版本
        simpleHttpResponse.setProtocol(request.getProtocol());
        // 1.1.2、状态码
        simpleHttpResponse.setStatusCode(200);

        // 1.2、响应头
        // 1.2.1、响应头
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Server", "SimpleHttpServer");
        simpleHttpResponse.setHeaders(headers);

        // 3、报文主体
        // 3.1、响应体
        // 利用 ReadFileUtil 从 resources/medicalArray.json 文件中随机读取一条数据
        List<MedicalRecordDTO> medicalList = ReadFileUtil.readAsList("medicalArray.json", MedicalRecordDTO.class);
        int randomIndex = (int) (Math.random() * medicalList.size());
        // 从列表中随机获取一个 MedicalRecordDTO 对象
        MedicalRecordDTO medicalRecordDTO = medicalList.get(randomIndex);
        // 将 MedicalRecordDTO 对象转换为 JSON 字符串
        String responseBody = JSON.toJSONString(medicalRecordDTO);
        // 设置响应体
        simpleHttpResponse.setBody(responseBody);

        // 3.2、更新 Content-Length
        headers.put("Content-Length", String.valueOf(responseBody.getBytes(StandardCharsets.UTF_8).length));

        return simpleHttpResponse;
    }
}
