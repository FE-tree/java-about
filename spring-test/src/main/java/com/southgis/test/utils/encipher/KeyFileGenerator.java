package com.southgis.test.utils.encipher;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

public class KeyFileGenerator {

    public static void generateAndSaveKey(String keyFilePath) throws Exception {
        // 生成AES密钥
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256, new SecureRandom()); // 可以使用128或192位
        SecretKey secretKey = keyGen.generateKey();

        // 方式1：直接序列化密钥对象（简单但不推荐用于生产）
        try (FileOutputStream fos = new FileOutputStream(keyFilePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(secretKey);
        }

        // 方式2：使用KeyStore存储（更安全，推荐）
        /*
        KeyStore ks = KeyStore.getInstance("JCEKS");
        ks.load(null, null); // 初始化空KeyStore
        KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(secretKey);
        KeyStore.ProtectionParameter password =
            new KeyStore.PasswordProtection("keystorePassword".toCharArray());
        ks.setEntry("aesKey", skEntry, password);

        try (FileOutputStream fos = new FileOutputStream(keyFilePath)) {
            ks.store(fos, "keystorePassword".toCharArray());
        }
        */
    }

    public static void main(String[] args) throws Exception {
        generateAndSaveKey("aes_secret.key");
        System.out.println("AES密钥已生成并保存到aes_secret.key文件");
    }
}
