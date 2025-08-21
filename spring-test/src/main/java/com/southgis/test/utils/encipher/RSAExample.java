package com.southgis.test.utils.encipher;

import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

// 非对称加密（RSA 示例）
// 非对称加密使用公钥加密，私钥解密。
public class RSAExample {
    public static void main(String[] args) throws Exception {
        // 生成密钥对
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(2048); // 2048位密钥
        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 加密
        String originalText = "Hello, World!";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encryptedBytes = cipher.doFinal(originalText.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
        System.out.println("加密后: " + encryptedText);

        // 解密
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedBytes);
        System.out.println("解密后: " + decryptedText);
    }
}