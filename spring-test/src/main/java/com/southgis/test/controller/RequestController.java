package com.southgis.test.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/request")
public class RequestController {

    public static String sendRequest(String url) {
        RestTemplate restTemplate = new RestTemplate();

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        //        headers.set("Accept", "application/json");
        //        headers.set("User-Agent", "MyJavaApp/1.0");
//        headers.set("Authorization", "Bearer your_token_here");
        //        headers.set("Custom-Header", "CustomValue");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            String.class
        );

        // 打印响应信息
        System.out.println("Status Code: " + response.getStatusCodeValue());
        System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response Body: " + response.getBody());

        // 返回响应体
        return response.getBody();
    }

    @RequestMapping("/url")
    public String requestUrl() {
        String response = sendRequest("http://172.16.32.23:33494/services/am-uaa/uaa/auth/user");
        System.out.println("Returned Response: " + response);

        return response;
    }
}
