package org.example.first.dao;

import org.example.first.config.HikariCPDataSource;
import org.example.first.utils.DBUtil;
import org.example.first.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 创建用户表
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL, " +
                "age INT, " +
                "email VARCHAR(100), " +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        DBUtil.executeUpdate(sql);
    }

    // 添加用户
    public int addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (name, age, email) VALUES (?, ?, ?)";
        return DBUtil.executeUpdate(sql, user.getName(), user.getAge(), user.getEmail());
    }

    // 根据ID查询用户
    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (ResultSet rs = DBUtil.executeQuery(sql, id)) {
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        }
        return null;
    }

    // 查询所有用户
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id";

        try (ResultSet rs = DBUtil.executeQuery(sql)) {
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        }
        return users;
    }

    // 更新用户
    public int updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, age = ?, email = ? WHERE id = ?";
        return DBUtil.executeUpdate(sql, user.getName(), user.getAge(), user.getEmail(), user.getId());
    }

    // 删除用户
    public int deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        return DBUtil.executeUpdate(sql, id);
    }

    // 从结果集提取用户对象
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    // 批量插入用户
    public int[] batchInsertUsers(List<User> users) throws SQLException {
        String sql = "INSERT INTO users (name, age, email) VALUES (?, ?, ?)";

        try (Connection conn = HikariCPDataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (User user : users) {
                pstmt.setString(1, user.getName());
                pstmt.setInt(2, user.getAge());
                pstmt.setString(3, user.getEmail());
                pstmt.addBatch();
            }

            return pstmt.executeBatch();
        }
    }
}
