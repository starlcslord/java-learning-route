package com.atguigu.exer;

import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Exer2Test {
    //问题1：向examstudent表中添加一条数据
    @Test
    public void testInsert(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入Type:");
        int Type = scanner.nextInt();
        System.out.println("请输入IDCard:");
        String IDCard = scanner.next();
        System.out.println("请输入ExamCard:");
        String ExamCard = scanner.next();
        System.out.println("请输入StudentName:");
        String StudentName = scanner.next();
        System.out.println("请输入Location:");
        String Location = scanner.next();
        System.out.println("请输入Grade:");
        int Grade = scanner.nextInt();
        String sql = "insert into examstudent(Type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
        int update = update(sql,Type, IDCard, ExamCard, StudentName, Location, Grade);
        if (update>0){
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
    //根据身份证或者查询准考证号查询学生成绩
    @Test
    public void TestgetForstudent(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请选择\nA:填写身份证号\nB:填写准考证号");
        String choose = scanner.next();
//        if (choose.compareTo("A")==0){
          if ("a".equalsIgnoreCase(choose)){
            String sql = "select FlowID,Type,IDCard,ExamCard,StudentName,Location,Grade from examstudent where IDCard = ?";
            System.out.println("填写身份证号");
            String num = scanner.next();
            examstudent instance = getInstance(examstudent.class, sql,num);
            if (instance!=null){
            System.out.println(instance);}
            else {
                System.out.println("查无此人");
            }
        }
//        else if (choose.compareTo("B")==0){
          else if ("b".equalsIgnoreCase(choose)){
            System.out.println("填写准考证号");
            String sql =  "select FlowID,Type,IDCard,ExamCard,StudentName,Location,Grade from examstudent where ExamCard = ?";
            String num = scanner.next();
            examstudent instance = getInstance(examstudent.class, sql, num);
            if (instance!=null){
                System.out.println(instance);}
            else {
                System.out.println("查无此人");
            }
        }
        else {
            System.out.println("你的输入有问题，请重新进入程序");
        }

    }
    /*
     * 针对不同表返回一条记录
     * */
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
    //删除指定的学生信息，如果不存在输出查无此人
    @Test
    public void testDeleteByExamCard(){
        System.out.println("请输入学生的考号");
        Scanner scanner = new Scanner(System.in);
        String num = scanner.next();
        String sql  = "select * from examstudent where ExamCard = ?";
        examstudent instance = getInstance(examstudent.class, sql, num);
        if (instance!=null){
            String sql1 = "delete from examstudent where ExamCard = ?";
            int update = update(sql1, num);
            if (update>0){
                System.out.println("成功删除");
            }else {
                System.out.println("删除失败");
            }
        }
        else{
            System.out.println("查无此人");
        }
    }
    //删除指定的学生信息，如果不存在输出查无此人
    @Test
    public void testDeleteByExamCardSimple(){
        System.out.println("请输入学生的考号");
        Scanner scanner = new Scanner(System.in);
        String num = scanner.next();
            String sql1 = "delete from examstudent where ExamCard = ?";
            int update = update(sql1, num);
            if (update>0){
                System.out.println("成功删除");
            }else {
                System.out.println("查无此人");
            }
        }
}
