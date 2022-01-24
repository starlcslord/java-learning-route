package com.atguigu.blob;

import com.atguigu.bean.Customer;
import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.sql.*;

/**
 * 使用Blob类型的数据
 */
public class BlobTest {
    //向数据表中customers中插入Blob类型的字段
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "insert into customers(name,email,birth,photo)values (?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,"Violet");
            ps.setObject(2,"VE@126.com");
            ps.setObject(3,"1992-09-07");
            FileInputStream is = new FileInputStream(new File("Violet Evergarden.bmp"));
            ps.setBlob(4,is);
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps);

        }
    }
    //查询数据表customers中的Blob类型的字段
    @Test
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id,name,email,birth,photo from customers where  id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,22);
            rs = ps.executeQuery();
            is = null;
            fos = null;
            if (rs.next()){
    //            方式一
    //            int id = rs.getInt(1);
    //            String name  = rs.getString(2);
    //            String email = rs.getString(3);
    //            Date birth = rs.getDate(4);
                //方式二
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");
                Customer cust = new Customer(id, name, email, birth);
                System.out.println(cust);
                //将Blob类型的字段下载下来，保存在本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("VE.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is!=null){
                is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos!=null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeConnection(conn,ps,rs);

        }

    }
}
