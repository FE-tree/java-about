package org.example.first.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController  {

    private static Map<Integer, String> users = new HashMap<>();

    // 初始化用户数据
    static {
        users.put(1, "John Doe");
        users.put(2, "Jane Doe");
    }

    // 获取所有用户
    @GetMapping
    public Map<Integer, String> getAllUsers() {
        return users;
    }

    // 获取特定用户
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Integer id) {
        return users.get(id);
    }

    // 创建新用户
    @PostMapping
    public String createUser(@RequestParam String name) {
        int id = users.size() + 1;
        users.put(id, name);
        return "User created with ID: " + id;
    }

    // 更新用户
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestParam String name) {
        users.put(id, name);
        return "User updated";
    }

    // 删除用户
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        users.remove(id);
        return "User deleted";
    }
}
