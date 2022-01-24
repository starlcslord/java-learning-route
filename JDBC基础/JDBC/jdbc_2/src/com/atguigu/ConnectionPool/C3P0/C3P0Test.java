package com.atguigu.ConnectionPool.C3P0;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.junit.jupiter.api.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Test {
    @Test
    public void testConnection(){
        //方式一
        //获取c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
        cpds.setUser("root");
        cpds.setPassword("root");
        //通过设置相关的参数，对数据库连接池进行管理
        //设置初始时数据库连接池的连接数
        cpds.setInitialPoolSize(10);
        Connection conn = null;
        try {
            conn = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(conn);

        try {
            //销毁连接池，一般不会执行该操作
            DataSources.destroy(cpds);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //方式二：使用配置文件
    @Test
    public void testconnection2() throws Exception {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);

    }
}
