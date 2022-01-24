package com.atguigu.dbutils;

import com.atguigu.DAO.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *commons-utils是一个由Apache组织提供的一个开源jdbc工具类库，封装了针对于数据库的增删改查的操作
 */
public class QueryRunnerTest {
    //测试插入
    @Test
    public void testInsert()  {
        Connection connection = null;
        try {
            QueryRunner runner  = new QueryRunner();
            connection = DruidJDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth)values (?,?,?)";
            int insertcount = runner.update(connection, sql, "李白", "libai@126.com", "1000-01-01");
            System.out.println("添加了"+insertcount+"条记录");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }
    //测试查询

    /**
     *BeanHandler是ResultSetHandler接口的实现类，用于封装表中的一条记录
     * @throws SQLException
     */
    @Test
    public void testQuery1() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidJDBCUtils.getConnection();
            String sql ="select id,name,email,birth from customers where id  = ?";
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);
            Customer customer = runner.query(connection, sql, handler, 23);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }
    /**
     *BeanListHandler是ResultSetHandler接口的实现类，用于封装表中的多条条记录
     */
    @Test
    public void testQuery2() {
        QueryRunner runner = new QueryRunner();
        Connection connection = null;
        List<Customer> customers = null;
        try {
            connection = DruidJDBCUtils.getConnection();
            String sql ="select id,name,email,birth from customers where id  < ?";
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);
            customers = runner.query(connection, sql, handler, 23);
            customers.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }
    /**
     *MapHandle是ResultSetHandler接口的实现类，用于对应表中的一条记录
     * 将字段及相应的值作为map的key和value
     */
    @Test
    public void testQuery3() {
        QueryRunner runner = new QueryRunner();
        Connection connection = null;
        try {
            connection = DruidJDBCUtils.getConnection();
            String sql ="select id,name,email,birth from customers where id  = ?";
            MapHandler handler = new MapHandler();
            Map<String,Object> customer = runner.query(connection, sql, handler, 23);
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }
    /**
     *MapListHandle是ResultSetHandler接口的实现类，用于对应表中的多条条记录
     * 将字段及相应的值作为map的key和value.将这些mao添加到list中
     */
    @Test
    public void testQuery4() {
        QueryRunner runner = new QueryRunner();
        Connection connection = null;
        try {
            connection = DruidJDBCUtils.getConnection();
            String sql ="select id,name,email,birth from customers where id  < ?";
            MapListHandler handler = new MapListHandler();
            List<Map<String,Object>> customers= runner.query(connection, sql, handler, 23);
            customers.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }

    /**
     * 使用ScalarHandler查询特殊值，例如个数，最大最小，引擎
     */
    @Test
    public void testQuery5() {
        QueryRunner runner = new QueryRunner();
        Connection connection = null;
        try {
            connection = DruidJDBCUtils.getConnection();
            String sql ="select count(*) from customers";
            ScalarHandler scalarHandler = new ScalarHandler();
            Long count =(Long) runner.query(connection, sql, scalarHandler );
            System.out.println("一共有"+count+"记录");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }
    @Test
    public void testQuery6() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidJDBCUtils.getConnection();
            String sql ="select MAX(birth) from customers";
            ScalarHandler scalarHandler = new ScalarHandler();
            Date birth =(Date) runner.query(connection, sql, scalarHandler );
            System.out.println("最年轻的生日"+birth);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }

    /**
     * 自定义ResultSetHandler的实现类
     */
    @Test
    public void testQuery7() {
        Connection connection = null;
        try {
            QueryRunner runner = new QueryRunner();
            connection = DruidJDBCUtils.getConnection();
            String sql ="select id,name,email,birth from customers where  id  = ?";
            ResultSetHandler<Customer> handler = new ResultSetHandler<Customer>() {
                @Override
                public Customer handle(ResultSet resultSet) throws SQLException {
//                    System.out.println("handler");
//                    return new Customer(12,"成龙","Jacky@126.com",new Date(242441223L));
                    if (resultSet.next()){
                        int id= resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String email = resultSet.getString("email");
                        Date birth = resultSet.getDate("birth");
                        Customer customer = new Customer(id,name,email,birth);
                        return customer;
                    }
                    return null;
                }
            };
            Customer customer =runner.query(connection, sql, handler,23 );
            System.out.println(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidJDBCUtils.closeConnection(connection,null);
        }

    }
}
