package com.southgis.test.utils.encipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

// 对称加密（AES 示例）
// 对称加密使用相同的密钥进行加密和解密。
public class AESExample2 {
    public static void main(String[] args) throws Exception {
        // 生成密钥
        SecretKey secretKey = generateAESKey(128);

        // 原始文本
        String originalText = "Hello, World!";

        // 加密
        String encryptedText = encrypt(originalText, secretKey);
        System.out.println("加密后: " + encryptedText);

        // 解密
        String decryptedText = decrypt(encryptedText, secretKey);
        System.out.println("解密后: " + decryptedText);
    }

    /**
     * 生成AES密钥
     * @param keySize 密钥长度（128, 192, 256）
     * @return SecretKey
     * @throws Exception
     */
    public static SecretKey generateAESKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        return keyGen.generateKey();
    }

    /**
     * AES加密
     * @param plainText 明文
     * @param secretKey 密钥
     * @return Base64编码的加密字符串
     * @throws Exception
     */
    public static String encrypt(String plainText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * AES解密
     * @param encryptedText Base64编码的加密字符串
     * @param secretKey 密钥
     * @return 解密后的明文
     * @throws Exception
     */
    public static String decrypt(String encryptedText, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}