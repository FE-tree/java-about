package com.southgis.test.example;

import com.southgis.utils.AESEncryptUtils;
import com.southgis.test.utils.JsonFileReader;

public class TestAESEncrypt {
    public static void main(String[] args) {
        try {
            String jsonString = JsonFileReader.readFromClasspath("static/testData.json");
            System.out.println("jsonReader: " + jsonString);
            String encrypted = AESEncryptUtils.encrypt(jsonString);
            System.out.println("Encrypted: " + encrypted);
            String decrypted = AESEncryptUtils.decrypt(encrypted);
            System.out.println("Decrypted: " + decrypted);
        } catch (Exception e) {
            System.err.println("Encryption failed: " + e.getMessage());
        }
    }
}
