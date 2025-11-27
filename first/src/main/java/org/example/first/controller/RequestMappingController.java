package org.example.first.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/request")
public class RequestMappingController  {
    @RequestMapping("/requestMapping")
    @ResponseBody
    public String requestMapping(){
        return "success";
    }

    @GetMapping("/getRequest")
    @ResponseBody
    public String getRequest(){
        return "getRequest success";
    }

    @PostMapping("/postRequest")
    @ResponseBody
    public String postRequest(){
        return "postRequest success";
    }

}
