package com.atguigu.transaction;

import com.atguigu.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionTest {
    @Test
    public void ConnTest() throws Exception {
        Connection connection = JDBCUtils.getConnection();
        String sql = "select * from `order`";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet resultSet = ps.executeQuery();
        JDBCUtils.closeConnection(connection,ps);
    }
}
