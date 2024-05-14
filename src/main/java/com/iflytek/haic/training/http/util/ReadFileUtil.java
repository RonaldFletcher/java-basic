package com.iflytek.haic.training.http.util;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static java.lang.System.err;

/**
 * 读取 resources 目录下json文件工具
 * 一定要是在resources目录下，否则读取不到
 */
public class ReadFileUtil {
    private ReadFileUtil() {
        // 工具类，私有构造方法，防止实例化
    }

    /**
     * 从 resources 目录下读取文件，返回字符串
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    public static String readAsString(String fileName) {
        URL resource = ReadFileUtil.class.getClassLoader().getResource("");
        if (resource == null) {
            throw new RuntimeException("获取 resources 目录失败");
        }
        try {
            Path path = Paths.get(resource.toURI()).toAbsolutePath();
            Path resourcesDir = path.resolve("..").resolve("..").resolve("..")
                    .resolve("src").resolve("main")
                    .resolve("resources").normalize();

            Path filePath = resourcesDir.resolve(fileName);

            if (!Files.exists(filePath)) {
                throw new RuntimeException("resources 目录下没有 " + fileName);
            }

            try (InputStream inputStream = Files.newInputStream(filePath);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString().trim();
            }
        } catch (URISyntaxException | IOException e) {
            System.err.printf("读取 resources 目录下 %s 发生错误\n", fileName);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从 resources 目录下读取文件，并转为对象
     * 文本内容必须是 Json 对象，否则异常
     *
     * @param fileName 文件名
     * @param type     需要转换的对象类型
     * @param <T>      对象类型泛型参数
     * @return 对象实例
     */
    public static <T> T readAsObject(String fileName, Class<T> type) {
        String jsonStr = readAsString(fileName);
        if (jsonStr.isEmpty()) {
            throw new RuntimeException("文本内容为空");
        }

        return JSON.parseObject(jsonStr, type);
    }

    /**
     * 从 resources 目录下读取文件，并转为对象列表
     * 文本内容必须是 Json 数组，否则异常
     *
     * @param fileName 文件名
     * @param type     需要转换的对象类型
     * @param <T>      对象类型泛型参数
     * @return 对象实例
     */
    public static <T> List<T> readAsList(String fileName, Class<T> type) {
        String jsonStr = readAsString(fileName);
        if (jsonStr.isEmpty()) {
            throw new RuntimeException("文本内容为空");
        }

        return JSON.parseArray(jsonStr, type);
    }
}
