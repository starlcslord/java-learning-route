package com.atguigu.DAO;

import com.atguigu.util.JDBCUtils;
import com.mysql.jdbc.log.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOImplTest {
    CustomerDAOImpl dao = new CustomerDAOImpl();
    @Test
    void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer cust = new Customer(1, "小飞", "xiaofei@126.com", new Date(1231314144L));
            dao.insert(conn,cust);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }

    @Test
    void deleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            int id = 1;
            dao.deleteById(conn,id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            JDBCUtils.closeConnection(conn,null);        }

    }

    @Test
    void update() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer cust = new Customer(23, "小红", "123131313@qq.com", new Date(89898998899898L));
            dao.update(conn,cust);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }

    @Test
    void getCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            int id = 1;
            Customer cust = dao.getCustomerById(conn,id);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }

    @Test
    void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Customer> all = dao.getAll(conn);
            all.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }

    @Test
    void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println("表中的记录"+count);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }

    @Test
    void getMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println("最大生日为"+maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }
}