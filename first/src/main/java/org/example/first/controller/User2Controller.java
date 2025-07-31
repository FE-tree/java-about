package org.example.first.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class User2Controller {

    @RequestMapping(path = "/login", headers="Host=localhost:8000")
    public String login() {
        return "success";
    }

    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public String show(@PathVariable("id") Integer id) {
        System.out.println("查看id为：" + id + "的user");
        return "success";
    }


    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public String update(@PathVariable("id") Integer id) {
        System.out.println("更新id为：" + id + "的user");
        return "success";
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String destroy(@PathVariable("id") Integer id) {
        System.out.println("删除id为：" + id + "的user");
        return "success";
    }

    @RequestMapping(value="", method=RequestMethod.POST)
    public String create() {
        System.out.println("新建user");
        return "success";
    }
}
