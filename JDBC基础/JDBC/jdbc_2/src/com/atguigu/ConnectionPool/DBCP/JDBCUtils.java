package com.atguigu.ConnectionPool.DBCP;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    /**
     * 使用dbcp获取连接,记得一定要把池子配置拿出来
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
            FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            //创建一个DBCP连接池
            dataSource = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        return connection;
    }
}
