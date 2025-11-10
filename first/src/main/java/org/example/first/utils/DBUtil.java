package org.example.first.utils;

import org.example.first.config.HikariCPDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    // 执行查询，返回结果集
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = HikariCPDataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        setParameters(pstmt, params);
        return pstmt.executeQuery();
    }

    // 执行更新（INSERT, UPDATE, DELETE）
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (Connection conn = HikariCPDataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        }
    }

    // 设置预处理参数
    private static void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }

    // 关闭资源
    public static void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}