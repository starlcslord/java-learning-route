package com.atguigu.DAOver2;

import com.atguigu.DAO.Customer;
import com.atguigu.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOImplTest extends BaseDAO {
    private CustomerDAOImpl dao = new CustomerDAOImpl();
    @Test
    void insert() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void testUpdate() {
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
    }

    @Test
    void getCount() {
    }

    @Test
    void getMaxBirth() {
    }
}