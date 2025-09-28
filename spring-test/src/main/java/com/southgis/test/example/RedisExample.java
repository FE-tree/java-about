package com.southgis.test.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.southgis.test.utils.JsonFileReader;
import com.southgis.utils.AESEncryptUtils;
import com.southgis.test.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.RedisSystemException;

import java.util.concurrent.TimeUnit;

public class RedisExample {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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

    public void redisSaveData() {
        // redis缓存扫码用户信息
        String scanUserId = "1234567890";
        String encryptData = "";
        try {
            String jsonString = JsonFileReader.readFromClasspath("static/testData.json");
            encryptData = AESEncryptUtils.encrypt(jsonString);
            System.out.println("ProvinceUser_ScanInfo<><>" + jsonString);
            System.out.println("ProvinceUser_encryptData<><>" + encryptData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("ProvinceUser_scanUserId<><>" + scanUserId);
        String redisKey = "provinceScanInfo_" + scanUserId;
        try {
            // 执行set操作
            redisTemplate.opsForValue().set(redisKey, encryptData, 12, TimeUnit.HOURS);
            // 验证操作是否成功
            Object redisResult = redisTemplate.opsForValue().get(redisKey);
            System.out.println("Set操作验证: " + (redisResult != null ? "成功" : "失败"));
        } catch (RedisConnectionFailureException e) {
            System.err.println("Redis连接失败: " + e.getMessage());
        } catch (RedisSystemException e) {
            System.err.println("Redis系统异常: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("未知异常: " + e.getMessage());
        }

        String userData = redisTemplate.opsForValue().get(redisKey);
        System.out.println("ProvinceUser_redis key<><>" + userData);

    }
}