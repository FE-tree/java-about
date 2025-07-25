package com.southgis.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.southgis.test.utils.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Feng Haobin
 * @Date: 2025/7/24 15:47
 * @Description:
 */
@RestController
public class TestController {
    @GetMapping("/run")
    public String run() {
        return "运行正常";
    }

    @GetMapping("/testMap")
    public Map<String, Object> testMap() {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", System.currentTimeMillis());
        response.put("success", true);

        Map<String, Object> data = new HashMap<>();
        data.put("item1", "值1");
        data.put("item2", 123);

        response.put("data", data);
        return response;
    }

    @GetMapping("/testJson")
    public ObjectNode testJson() {
        ObjectMapper mapper = new ObjectMapper();

        // 创建JSON对象
        ObjectNode jsonObject = mapper.createObjectNode();
        // 添加基本类型
        jsonObject.put("name", "王五");
        jsonObject.put("age", 28);
        jsonObject.put("isStudent", false);

        // 添加嵌套对象
        ObjectNode address = mapper.createObjectNode();
        address.put("city", "广州");
        address.put("street", "天河区");
        jsonObject.set("address", address);

        // 添加数组
        ArrayNode hobbies = mapper.createArrayNode();
        hobbies.add("篮球");
        hobbies.add("音乐");
        hobbies.add("电影");
        jsonObject.set("hobbies", hobbies);

        // 输出JSON字符串
        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    @GetMapping("/testRes")
    public ApiResponse<Map<String, Object>> testRes() {
        Map<String, Object> data = new HashMap<>();
        data.put("item1", "值1");
        data.put("item2", 123);

        // 输出JSON字符串
        System.out.println(data.toString());
        System.out.println(ApiResponse.success(data).toString());
        return ApiResponse.success(data);
    }
}
