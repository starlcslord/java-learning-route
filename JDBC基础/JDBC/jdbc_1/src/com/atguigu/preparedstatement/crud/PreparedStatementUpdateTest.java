package com.atguigu.preparedstatement.crud;

import ConnectionTest.ConnectionTest1;
import com.atguigu.utils.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * 使用PreparedStatement来替换Statement,实现对数据表的增删改查操作
 * 增删改：不需要返回
 *
 * 查：需要返回数据
 */
public class PreparedStatementUpdateTest {
    @Test
    public  void testUpdate1(){
//        //删除
//        String sql = "delete from customers where id = ?";
//        update(sql,20);
        String sql = "update `order` set order_name = ? where order_id=?";
        update(sql,"DD","2");
    }
    //通用的增删改操作
    public  void update(String sql,Object ...args){
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            //args sql中的占位符的个数与可变形参的长度相同
            connection = JDBCUtils.getConnection();
            ps = connection.prepareStatement(sql);
            //填充占位符
            for(int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);//小心参数序号
            }
            //执行
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            //关闭
            JDBCUtils.closeConnection(connection, ps);
        }
    }
    //向customers表中添加一条记录
    @Test
    public void testInsert(){
        //获取加载器
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            InputStream is = PreparedStatementUpdateTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driver=pros.getProperty("driver");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
            System.out.println(connection);
            //4.预编译sql语句，返回PreparedStatement实例
            String sql="insert into customers(name,email,birth)values(?,?,?)"; //?占位符
            ps = connection.prepareStatement(sql);
            //5.填充占位符,
            ps.setString(1,"哪吒"); //索引从一开始
            ps.setString(2,"nezha@gmail.com");
            //日期设置
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date = sdf.parse("1000-01-01");
            ps.setDate(3,new Date(date.getTime()));
            //执行操作
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }finally {
            //资源关闭
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

    }
    @Test
    public  void testUpdate() {
        //1.获取数据库连接
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCUtils.getConnection();
            //2.执行sql语句
            //2.1预编译sql语句,返回PreparedStatement的实例
            String sql = "update  customers set name = ? where  id = ?";
            ps = connection.prepareStatement(sql);
            //2.2填充占位符
            ps.setObject(1,"莫扎特");
            ps.setObject(2,18);
            //2.3执行
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //3.关闭连接
        finally {
            JDBCUtils.closeConnection(connection,ps);
        }

    }
    //通用查询功能
    @Test
    public ResultSet cx(String sql,Object ...args) throws SQLException, IOException, ClassNotFoundException {
        PreparedStatement ps = null;
        //1.获取连接
        Connection connection = JDBCUtils.getConnection();
        //2.预编译
        ps = connection.prepareStatement(sql);
        //3.填充占位符
        for (int i=0;i<args.length;i++){
            ps.setObject(i+1,args[i]);
        }
        //执行
        ResultSet rs= ps.executeQuery();
        return rs;
    }
    @Test
    public void testcx() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from customers";
        cx(sql);
    }
}
