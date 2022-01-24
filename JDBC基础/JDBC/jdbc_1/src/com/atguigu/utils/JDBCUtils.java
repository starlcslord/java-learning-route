package com.atguigu.utils;

import ConnectionTest.ConnectionTest1;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    //获取连接方法
    public static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {
        //1.读取配置文件的4个基本信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driver = pros.getProperty("driver");
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println(connection);
        return connection;
    }
    //关闭连接
    public static void  closeConnection(Connection connection, PreparedStatement ps){
        try {
            if (ps != null) //避免空指针
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void  closeConnection(Connection connection, PreparedStatement ps, ResultSet resultSet){
        try {
            if (ps != null) //避免空指针
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (resultSet != null) //避免空指针
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
