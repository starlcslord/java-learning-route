package com.atguigu.blob;

import com.atguigu.utils.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *使用PreparedStatement实现批量数据的操作
 * update,delete本身就具有批量操作的效果
 * 此时的批量操作，主要指的是批量插入，使用PreparedStatement如何实现更加高效的批量插入？
 * insert
 * 方式一：statement
 * Connection conn = JDBCUtils.getConnection()
 * Statement st  = conn.createStatement();
 * for(int i = 1;i<20000;i++){
 *     String sql = "insert into goods(name)values('name_"+i+"')";
 *     st.execute(sql);
 * }
 */

public class InsertTest {
    //批量插入方式二
    @Test
    public void testInsert1() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            ps = null;
            String sql = "insert into goods(name)values (?)";
            ps = conn.prepareStatement(sql);
            for (int i=1;i<=20000;i++) {
                ps.setObject(1,"name_"+i);//花费时间为36317
                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println("花费时间为"+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps);
        }

    }
    /**方式三、
     * 1.addBatch(),executeBatch(),clearBatch()  一波一波的执行，不会有一条就读写一条，而是有一波就读写一波
     * 2.mysql服务器默认是关闭批处理的，我们需要通过一个参数，才能让mysql开启批处理的支持
     * ?rewriteBatchedStatements=true 写在配置文件的url后面
     * mysql5.1.37版本
     */
    @Test
    public void testInsert2() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            ps = null;
            String sql = "insert into goods(name)values (?)";
            ps = conn.prepareStatement(sql);
            for (int i=1;i<=1000000;i++) {
                ps.setObject(1,"name_"+i);//2W 花费时间为553      100W   花费时间为8233
                //1、攒sql
                ps.addBatch();
                if (i%500 == 0){
                    //2、执行攒的500条sql语句
                    ps.executeBatch();
                    //3、清空Batch
                    ps.clearBatch();
                }

            }
            long end = System.currentTimeMillis();
            System.out.println("花费时间为"+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps);
        }

    }
    //方法四、一波一波执行之后，但不提交，最后在提交，因为提交也要时间   设置不允许自动提交
    @Test
    public void testInsert3() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            //设置不允许自动提交
            conn.setAutoCommit(false);
            ps = null;
            String sql = "insert into goods(name)values (?)";
            ps = conn.prepareStatement(sql);
            for (int i=1;i<=1000000;i++) {
                ps.setObject(1,"name_"+i);//100W 花费时间为4408
                //1、攒sql
                ps.addBatch();
                if (i%500 == 0){
                    //2、执行攒的500条sql语句
                    ps.executeBatch();
                    //3、清空Batch
                    ps.clearBatch();
                }
            }
            conn.commit();
            long end = System.currentTimeMillis();
            System.out.println("花费时间为"+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps);
        }

    }
}
