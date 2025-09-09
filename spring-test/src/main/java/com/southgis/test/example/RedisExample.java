package com.southgis.test.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.southgis.test.model.User;
import redis.clients.jedis.Jedis;

public class RedisExample {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // 连接到 Redis 服务器
        Jedis jedis = new Jedis("localhost", 6379);
        System.out.println("Connection to server successfully");
        // 检查服务器是否运行
        System.out.println("Server is running: "+jedis.ping());

        jedis.set("name", "tree1"); // 设置键值对
        String value = jedis.get("name"); // 获取值
        System.out.println("Value of name: " + value);

        jedis.set("nick_name", "tree2"); // 设置键值对
        String value2 = jedis.get("nick_name"); // 获取值
        System.out.println("Value of name: " + value2);

        User user = new User("tree", 111, "a@example.com");
        jedis.set("province_userinfo:id123456", objectMapper.writeValueAsString(user));
        String userVal = jedis.get("user:id123456");
        System.out.println("Value of user: " + userVal);

        User user2 = new User("bbb", 222, "b@example.com");
        jedis.set("user:json:2", objectMapper.writeValueAsString(user2));
        String userVal2 = jedis.get("user:json:2");
        System.out.println("Value of user: " + userVal2);

        // 关闭连接
        jedis.close();
    }
}