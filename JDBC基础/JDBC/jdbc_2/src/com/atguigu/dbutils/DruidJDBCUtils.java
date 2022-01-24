package com.atguigu.dbutils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public  class  DruidJDBCUtils {
    private static DataSource dataSource;
    static {
        try {
            Properties pros = new Properties();
            //方式一
//        InputStream is  = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
            //方式二
            FileInputStream is = new FileInputStream(new File("src/druid.properties"));
            pros.load(is);
            //创建一个DBCP连接池
            dataSource = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        return connection;
    }
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
    public static void closeConnection(Connection connection, PreparedStatement ps, ResultSet rs){
        try {
            if (connection !=null)
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {if (ps!=null)
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {if (rs!=null)
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用DBUtils的DBUtils的工具类实现关闭
     */
    public static void closeResource(Connection connection, Statement ps,ResultSet rs){
        try {
            DbUtils.close(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DbUtils.close(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            DbUtils.close(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeResource1(Connection connection, Statement ps,ResultSet rs){
        DbUtils.closeQuietly(connection);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }
}
