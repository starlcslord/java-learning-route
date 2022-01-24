package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.bean.Order;
import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 使用PreparedStatement实现针对不同表的通用的查询条件
* */
public class PreparedStatementQueryTest {
    @Test
    public void testGetInstance(){
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id=?";
        Order instance = getInstance(Order.class, sql, 1);
        System.out.println(instance);
        String sql1 = "select id,name,birth,email from customers where id = ?";
        Customer instance1 = getInstance(Customer.class, sql1, 1);
        System.out.println(instance1);
    }
    @Test
    public  void testGetForList(){
        String sql = "select id,name,email,birth from customers where id < ?";
        List<Customer> forList = getForList(Customer.class, sql, 12);
        forList.forEach(System.out::println);
        String sql1 = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id < ?";
        List<Order> Listorder =  getForList(Order.class,sql1,5);
        Listorder.forEach(System.out::println);
    }
    /*
     * 针对不同表返回多条记录
     * */
    public <T> List<T>  getForList(Class<T> clazz, String sql, Object...args){
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
            //创建集合对象
            ArrayList<T> arrayList = new ArrayList<T>();
            while (rs.next()){
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
                arrayList.add(t);
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps,rs);
        }
        return null;
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
}
