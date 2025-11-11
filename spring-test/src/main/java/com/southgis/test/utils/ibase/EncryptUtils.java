package com.southgis.test.utils.ibase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EncryptUtils {

    private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 利用java原生的类实现SHA256加密
     *
     * @param str         明文
     * @param isUpperCase 是否转大写
     * @return 大写密文
     */
    public static String getSHA256(String str, boolean isUpperCase) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
            if (isUpperCase) {
                encodeStr = encodeStr.toUpperCase();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }

    /**
     * 将byte转为16进制
     *
     * @param bytes 字节数组
     * @return 16进制字符
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte by : bytes) {
            temp = Integer.toHexString(by & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    /**
     * 返回一个定长的随机字符串(包含数字或者大小写字母)
     *
     * @param length 随机数的长度
     * @return 随机数
     */
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length())));
        }
        return sb.toString();
    }
}
