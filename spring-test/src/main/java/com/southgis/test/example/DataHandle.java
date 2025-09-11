package com.southgis.test.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.southgis.test.utils.JsonFileReader;

import java.io.IOException;

public class DataHandle {
    public static void main(String[] args) throws IOException {
        /*String jsonString = JsonFileReader.readFromClasspath("static/userResult.json");
        System.out.println("jsonString: " + jsonString);*/

        // 使用示例
        /*String jsonString = JsonFileReader.readFromClasspath("static/userResult.json");
        JsonNode jsonNode = JsonFileReader.stringToJson(jsonString);
        System.out.println("jsonString: " + jsonNode);
        System.out.println("jsonString: " + jsonNode.get("code"));
        System.out.println("jsonString: " + JsonFileReader.jsonNodeToString(jsonNode.get("data")));
        System.out.println("jsonString: " + jsonNode.get("data").get("user"));
        System.out.println("jsonString: " + jsonNode.get("data").get("user").get("id"));*/

        String jsonString = JsonFileReader.readFromClasspath("static/testData.json");
        JsonNode jsonNode = JsonFileReader.stringToJson(jsonString);
        System.out.println("jsonString: " + jsonNode.get("code"));
        System.out.println("jsonString: " + JsonFileReader.jsonNodeToString(jsonNode.get("data")));
        System.out.println("jsonString: " + jsonNode.get("data").get("user"));
        System.out.println("jsonString: " + jsonNode.get("data").get("user").get("username"));
    }
}
