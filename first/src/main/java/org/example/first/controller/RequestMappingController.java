package org.example.first.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RequestMappingController  {
    @RequestMapping("/requestMapping")
    @ResponseBody
    public String requestMapping(){
        return "success";
    }

}
