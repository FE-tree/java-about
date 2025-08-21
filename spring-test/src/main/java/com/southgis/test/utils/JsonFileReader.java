package com.southgis.test.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * JSON 文件读取工具类
 */
public class JsonFileReader {

    /**
     * 从 classpath 读取 JSON 文件内容
     *
     * @param filePath 文件路径，相对于 resources 目录，如 "static/config.json"
     * @return JSON 字符串
     * @throws IOException 文件读取异常
     * @throws IllegalArgumentException 文件不存在异常
     */
    public static String readFromClasspath(String filePath) throws IOException {
        if(Objects.equals(filePath, "")) {
            filePath = "static/userResult.json";
        }
        try (InputStream inputStream = getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in classpath: " + filePath);
            }
            return readFromInputStream(inputStream);
        }
    }

    /**
     * 从 InputStream 读取内容
     *
     * @param inputStream 输入流
     * @return 文件内容字符串
     * @throws IOException 读取异常
     */
    public static String readFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }

    /**
     * 获取资源文件的 InputStream
     *
     * @param filePath 文件路径
     * @return InputStream 或 null(如果文件不存在)
     */
    private static InputStream getResourceAsStream(String filePath) {
        // 优先使用当前类的 ClassLoader
        InputStream inputStream = JsonFileReader.class.getClassLoader().getResourceAsStream(filePath);
        // 如果没找到，尝试使用系统 ClassLoader
        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream(filePath);
        }
        return inputStream;
    }
}
