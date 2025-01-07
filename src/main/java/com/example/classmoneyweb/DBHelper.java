package com.example.classmoneyweb;

import java.sql.*;

/**
 * 数据库帮助类v3.0
 */
public class DBHelper {
    private static String driver = "com.mysql.cj.jdbc.Driver";            // 驱动类完整名称
    private static String url = "jdbc:mysql://localhost:3306/kaoshi";        // 数据库连接地址
    private static String username = "root";                            // 数据库登录用户名
    private static String password = "root";                            // 数据库登录密码

    // 在静态代码块中加载数据库驱动（只执行一次）
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行更新语句，返回影响行数
     *
     * @param sql    添加语句、修改语句、删除语句
     * @param params ?占位符参数值
     * @return 影响行数，-1表示执行错误
     */
    public static int getCount(String sql, Object... params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setParams(preparedStatement, params);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            close(connection, preparedStatement);
        }
    }

    /**
     * 设置操作对象参数
     *
     * @param preparedStatement 操作对象
     * @param params            参数列表
     */
    public static void setParams(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }


    /***
     * 获取数据库连接对象
     *
     * @return 数据库连接对象
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * 关闭连接，释放资源
     *
     * @param conn 连接对象
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接，释放资源
     *
     * @param stmt 传输器
     */
    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接，释放资源
     *
     * @param rs 结果集
     */
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭连接，释放资源
     *
     * @param conn 连接对象
     * @param stmt 传输器
     */
    public static void close(Connection conn, Statement stmt) {
        close(stmt);
        close(conn);
    }

    /**
     * 关闭连接，释放资源
     *
     * @param conn 连接对象
     * @param stmt 传输器
     * @param rs   结果集
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        close(rs);
        close(conn, stmt);
    }
}