package com.atguigu.exer;

import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Exer1Test {
    @Test
    public void testInsert(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String name = scanner.next();
        System.out.println("请输入邮箱:");
        String email = scanner.next();
        System.out.println("请输入生日:");
        String birthday = scanner.next();
        String sql = "insert into customers(name,email,birth)values(?,?,?)";
        int update = update(sql, name, email, birthday);
        if (update > 0){
            System.out.println("添加成功");
        }
        else {
            System.out.println("添加失败");
        }
    }
    //通用的增删改操作,通过boolean值判断是否增删改成功或失败
    public  int update(String sql,Object ...args){
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
            /*
            * ps.execute()是boolean
            * 如果执行的是查询操作，有返回结果，则此方法返回true
            * 如果执行的是增删改没有返回结果，则此方法返回的是false
            *
            * ps.executeUpdate()返回操作了几条记录
            * */
            return ps.executeUpdate();
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
        return  0;
    }
}
