package org.example.first.service;

import org.example.first.dao.UserDAO;
import org.example.first.model.User;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    // 初始化数据库
    public void initializeDatabase() throws SQLException {
        userDAO.createTable();
        System.out.println("用户表创建成功");
    }

    // 添加用户
    public void addUser(String name, int age, String email) throws SQLException {
        User user = new User(name, age, email);
        int result = userDAO.addUser(user);
        System.out.println("添加用户成功，影响行数: " + result);
    }

    // 批量添加用户
    public void batchAddUsers() throws SQLException {
        List<User> users = Arrays.asList(
                new User("张三", 25, "zhangsan@email.com"),
                new User("李四", 30, "lisi@email.com"),
                new User("王五", 28, "wangwu@email.com")
        );

        int[] results = userDAO.batchInsertUsers(users);
        System.out.println("批量插入完成，影响行数: " + Arrays.toString(results));
    }

    // 查询所有用户
    public void listAllUsers() throws SQLException {
        List<User> users = userDAO.getAllUsers();
        System.out.println("所有用户列表:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    // 更新用户
    public void updateUser(int id, String name, int age, String email) throws SQLException {
        User user = userDAO.getUserById(id);
        if (user != null) {
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            int result = userDAO.updateUser(user);
            System.out.println("更新用户成功，影响行数: " + result);
        } else {
            System.out.println("用户不存在");
        }
    }

    // 删除用户
    public void deleteUser(int id) throws SQLException {
        int result = userDAO.deleteUser(id);
        System.out.println("删除用户成功，影响行数: " + result);
    }
}