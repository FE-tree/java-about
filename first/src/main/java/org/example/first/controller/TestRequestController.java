package org.example.first.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/test")
public class TestRequestController  {
    @RequestMapping("/requestUrl")
    public String requestUrl(){
        return "success";
    }
}
