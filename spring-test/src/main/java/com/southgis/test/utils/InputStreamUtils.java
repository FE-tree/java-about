package com.southgis.test.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamUtils {

    /**
     * 复制InputStream（内存方式，适合小文件）
     */
    public static InputStream copyInputStream(InputStream original) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;

        while ((len = original.read(buffer)) > -1) {
            baos.write(buffer, 0, len);
        }
        baos.flush();

        return new ByteArrayInputStream(baos.toByteArray());
    }

    /**
     * 复制多个InputStream副本
     */
    public static InputStream[] copyInputStreams(InputStream original, int count) throws IOException {
        byte[] data = readAllBytes(original);
        InputStream[] copies = new InputStream[count];

        for (int i = 0; i < count; i++) {
            copies[i] = new ByteArrayInputStream(data);
        }

        return copies;
    }

    /**
     * 读取所有字节
     */
    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();

        return buffer.toByteArray();
    }
}