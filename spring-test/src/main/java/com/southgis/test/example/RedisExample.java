package com.southgis.test.example;

import redis.clients.jedis.Jedis;

public class RedisExample {
    public static void main(String[] args) {
        // 连接到 Redis 服务器
        Jedis jedis = new Jedis("localhost", 6379);
        System.out.println("Connection to server successfully");
        // 检查服务器是否运行
        System.out.println("Server is running: "+jedis.ping());

//        jedis.set("name", "tree1"); // 设置键值对
        String value = jedis.get("name"); // 获取值
        System.out.println("Value of name: " + value);

        // 关闭连接
        jedis.close();
    }
}