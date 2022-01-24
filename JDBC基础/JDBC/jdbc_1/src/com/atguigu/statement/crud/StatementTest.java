package com.atguigu.statement.crud;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
//使用Statement的弊端，需要拼写sql语句，存在sql注入问题
public class StatementTest {
    @Test
    public void testLogin() throws IOException, ClassNotFoundException, SQLException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("用户名");
        //nextline会出现sql注入
        String userName=scanner.nextLine();
        System.out.println("密码");
        String password=scanner.nextLine();
        String sql="select user,password from user_table where user='"+userName+"'and password='"+password+"'";
        User returnuser=get(sql,User.class);
        if (returnuser!=null){
            System.out.println("登录成功");
        }
        else {
            System.out.println("失败");
        }
        //
    }
    public <T> T get(String sql,Class<T> clazz) throws ClassNotFoundException, IOException, SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        T t=null;
        Connection connection = null;
        Statement statement=null;
        ResultSet resultSet=null;
        //1.加载配置文件
        InputStream inputStream=StatementTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(inputStream);
        //2.读取配置信息
        String user=properties.getProperty("user");
        String password=properties.getProperty("password");
        String url=properties.getProperty("url");
        String driver=properties.getProperty("driver");
        //3.加载驱动
        Class.forName(driver);
        //4.获取连接
        connection= DriverManager.getConnection(url,user,password);
        statement = connection.createStatement();
        resultSet=statement.executeQuery(sql);
        //获取结果集的元数据
        ResultSetMetaData rsmd=resultSet.getMetaData();
        //获取结果集的列数
        int columnCount = rsmd.getColumnCount();
        if (resultSet.next()){
            t = clazz.newInstance();
            for (int i=0;i<columnCount;i++){
                //1.获取列的别名
                String columnName= rsmd.getColumnLabel(i+1);
                //2.根据列名获取对应的数据表中的数据
                Object columnVal = resultSet.getObject(columnName);
                //3.将数据表中的数据，封装进对象
                Field field = clazz.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(t,columnVal);
            }

        }
        return t;
    }
}
