package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.bean.Order;
import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

public class OrderForQuery {
    @Test
    public void  testOrderForQuery(){
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id=?";
        Order  order = OrderForQuery(sql,1);
        System.out.println(order);
    }
    /*
     *针对于order表的查询操作
     */
    public Order OrderForQuery(String sql,Object...args)  {
        /*
        *表的字段名和类的属性名不一样的情况：
        * 必须声明sql时，使用类的属性名来命名字段的别名
        * 在ResultSetMetaData 使用getColumnLabel()来替换getColumnName()方法获取列的别名
        * 如果sql字段没有起别名，getColumnLabel()获取还是列名
        */
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
                Order order = new Order();

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
                    Field field = Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    //给属性设值，相当于setOrderId,setOrderName等
                    field.set(order,columnValue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps,rs);
        }
        return null;
    }

    @Test
    public void testQuery1(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1);

            rs = ps.executeQuery();
            if (rs.next()){
                int id = (int)rs.getObject(1);
                String name = (String) rs.getObject(2);
                Date date = (Date) rs.getObject(3);
                Order order = new Order(id,name,date);
                System.out.println(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps,rs);
        }

    }
    @Test
    public void testForQuery(){
        String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id=?";
        ForQuery(sql,1);
        String sql1 = "select id,name,birth,email from customers where id = ?";
        ForQuery(sql1,13);
    }
    /*
     *针对于order表的查询操作
     */
    public void ForQuery(String sql,Object...args)  {
        /*
         *表的字段名和类的属性名不一样的情况：
         * 必须声明sql时，使用类的属性名来命名字段的别名
         * 在ResultSetMetaData 使用getColumnLabel()来替换getColumnName()方法获取列的别名
         * 如果sql字段没有起别名，getColumnLabel()获取还是列名
         */
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

                for (int i=0;i<columnCount;i++){
                    //获取每一个列的列值 通过结果集ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //获取每个列的列名 通过元数据
                    //获取列的列名
                    //String columnName = rsmd.getColumnName(i+1);
                    //获取列的别名
                    String columnLabel = rsmd.getColumnLabel(i+1);
                    //columnValue是数据中存储的值
                    System.out.println(columnLabel);
                    System.out.println(columnValue);

//System.out.println(columnValue+columnLabel);
//1orderId
//AAorderName
//2010-03-04orderDate
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps,rs);
        }
    }

}
