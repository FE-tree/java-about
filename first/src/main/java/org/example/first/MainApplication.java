package org.example.first;

import org.example.first.config.HikariCPDataSource;
import org.example.first.service.UserService;

import java.sql.SQLException;

public class MainApplication {
    public static void main(String[] args) {
        UserService userService = new UserService();

        try {
            // 1. 初始化数据库
            userService.initializeDatabase();

            // 2. 添加单个用户
            userService.addUser("测试用户", 25, "test@email.com");

            // 3. 批量添加用户
            userService.batchAddUsers();

            // 4. 查询所有用户
            userService.listAllUsers();

            // 5. 更新用户（假设ID为1的用户存在）
            userService.updateUser(1, "修改后的名字", 26, "updated@email.com");

            // 6. 再次查询验证更新
            System.out.println("\n更新后的用户列表:");
            userService.listAllUsers();

            // 7. 删除用户
            userService.deleteUser(2);

            // 8. 最终的用户列表
            System.out.println("\n最终的用户列表:");
            userService.listAllUsers();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接池
            HikariCPDataSource.closeDataSource();
            System.out.println("连接池已关闭");
        }
    }
}
