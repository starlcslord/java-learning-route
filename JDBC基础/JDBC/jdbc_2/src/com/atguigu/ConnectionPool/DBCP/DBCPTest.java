package com.atguigu.ConnectionPool.DBCP;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPTest {
    /**
     * 测试DBCP的数据库的连接池技术
     */
    //方式一：不推荐
    @Test
    public void testGetConnection() throws SQLException {
        //创建了DBCP的数据库连接池
        BasicDataSource source = new BasicDataSource();
        //设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql:///test");
        source.setUsername("root");
        source.setPassword("root");
        //还可以设置相关属性
        source.setInitialSize(10);
        source.setMaxActive(10);

        Connection connection = source.getConnection();


    }
    //方式二：使用配置文件
    @Test
    public void testGetConnection1() throws Exception {
        Properties pros = new Properties();
        //方式一
//        InputStream is  = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
        //方式二
        FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
        pros.load(is);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(pros);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
}
