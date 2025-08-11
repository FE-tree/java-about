package com.southgis.test.encipher;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

// 非对称加密（RSA 示例）
// 非对称加密使用公钥加密，私钥解密。
public class RSAExample2 {

    public static void main(String[] args) throws Exception {
        // 生成密钥对
        KeyPair keyPair = generateRSAKeyPair(2048);

        // 原始文本
        String originalText = "Hello, World!";

        // 加密
        String encryptedText = rsaEncrypt(originalText, keyPair.getPublic());
        System.out.println("加密后: " + encryptedText);

        // 解密
        String decryptedText = rsaDecrypt(encryptedText, keyPair.getPrivate());
        System.out.println("解密后: " + decryptedText);
    }

    /**
     * 生成RSA密钥对
     * @param keySize 密钥长度（推荐2048或4096）
     * @return KeyPair
     * @throws Exception
     */
    public static KeyPair generateRSAKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(keySize);
        return keyPairGen.generateKeyPair();
    }

    /**
     * RSA加密
     * @param plainText 明文
     * @param publicKey 公钥
     * @return Base64编码的加密字符串
     * @throws Exception
     */
    public static String rsaEncrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * RSA解密
     * @param encryptedText Base64编码的加密字符串
     * @param privateKey 私钥
     * @return 解密后的明文
     * @throws Exception
     */
    public static String rsaDecrypt(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }
}