package com.atguigu.preparedstatement.crud;

import com.atguigu.bean.Customer;
import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.spi.CurrencyNameProvider;

/**
 * 针对于Customers表的查询操作
 */
public class CustomerForQuery {
    //针对与Customer表的通用查询
    @Test
    public Customer queryForCutomers(String sql,Object ...args){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();//获取连接
            preparedStatement = connection.prepareStatement(sql);//预编译
            for (int i = 0;i< args.length;i++){//设置sql语句where中的内容 ?
                preparedStatement.setObject(i+1,args[i]);
            }
            resultSet = preparedStatement.executeQuery(); //执行查找，返回结果集
            ResultSetMetaData rsmd = resultSet.getMetaData();//结果集的元数据
            int columnCount = rsmd.getColumnCount();//获取有多少列
            if (resultSet.next()){
                Customer cust = new Customer();
                //处理结果集一行数据中的每一列
                    for (int i  = 0 ;i<columnCount;i++){
                        Object columnValue = resultSet.getObject(i+1);
                        //获取每个列的列名
                        String columnLabel = rsmd.getColumnLabel(i + 1);
                        //给cust对象指定的columnName属性，赋值为columnValue，通过反射
                        Field field = Customer.class.getDeclaredField(columnLabel);
                        field.setAccessible(true);//设置可访问的
                        field.set(cust,columnValue);
                    }
                    return cust;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(connection,preparedStatement,resultSet);

        }
        return null;
    }
    @Test
    public  void testqueryForCutomers() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select  id,name,email,birth from customers where name = ?";
        String name = "汪峰";
        Customer customer = queryForCutomers(sql, name);
        System.out.println(customer);
        String sql1 = "select id,name,birth,email from customers where id = ?";
        Customer customer1 = queryForCutomers(sql1, 13);
        System.out.println(customer1);
    }
    /**
     *
     */
    @Test
    public  void testQuery1() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            String sql="select  id,name,email,birth from customers where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            preparedStatement.setObject(1,"1");
            //执行并返回结果集
            resultSet = preparedStatement.executeQuery();
            //处理结果集
            if (resultSet.next()){ //next() 判断结果集下条是否有数据，如果有数据返回true,如果返回false，返回下条结果集
                //获取当前这条数据的各个字段值
                int id= resultSet.getInt(1);
                String name=resultSet.getString(2);
                String email = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                //方式一
    //            System.out.println("id="+id+"name="+name+"email="+email+"Date="+date);
                //使用customer的toString()
                Customer customer = new Customer(id,name,email,date);
                System.out.println(customer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //关闭资源
        JDBCUtils.closeConnection(connection,preparedStatement,resultSet);
    }
}
