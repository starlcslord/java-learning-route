package com.atguigu.statement.crud;

import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class PreparedStatementTest {
    /*
    * 解决sql注入问题
    * 除了解决拼串,sql注入问题，还有哪些好处？
    * 1.PreparedStatement操作Blob的数据,而Statement做不到
    * 2.可以更加高效的插入、、
    * */
    @Test
    public  void testLogin(){
        Scanner scanner   = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String user = scanner.nextLine();
        System.out.println("请输入密码");
        String password = scanner.nextLine();
        String sql = "select user,password from user_table where user = ? and password = ?";
        User returnUser = getInstance(User.class, sql, user, password);
        if (returnUser != null){
            System.out.println("登录成功");
        }
        else {
            System.out.println("用户名不存在或密码错误");
        }

    }
    public <T> T getInstance(Class<T> clazz, String sql,Object...args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0 ;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //执行获取结果集
            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                T t = clazz.newInstance();
                for (int i=0;i<columnCount;i++){
                    //获取每一个列的列值 通过结果集ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //获取每个列的列名 通过元数据
                    //获取列的列名
                    //String columnName = rsmd.getColumnName(i+1);
                    //获取列的别名
                    String columnLabel = rsmd.getColumnLabel(i+1);
                    //通过反射，将对象指定名columnName的属性赋值为指定的值columnValue
                    //获取对象的属性
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    //给属性设值，相当于setOrderId,setOrderName等
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps,rs);
        }
        return null;
    }
}
