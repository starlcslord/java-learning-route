package com.atguigu.ConnectionPool.Druid;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class JDBCUtils {
    /**
     * 使用druid获取连接,记得一定要把池子配置拿出来
     * @return
     *
     */
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
    public static Connection getConnection() throws Exception {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        return connection;
    }
    @Test
    public void testGetConnnection () throws Exception {
        Connection connection = getConnection();
        System.out.println(connection);
    }
}
