package com.atguigu.transaction;

import com.atguigu.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * 1.什么叫数据库事务
 * 事务：一组逻辑操作单元，使数据从一种状态变化到另一种状态
 *      >一组逻辑操作单元，一个或多个DML操作
 * 2.事务处理的原则：
 * 保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式，当一个事务中执行多个操作时，要么所有的事务都被提交(commit)，那么这些
 * 修改就永远保存下来，要么数据库管理系统将放弃所做的所有修改，整个事务回滚（rollback）到最初状态
 *3.数据一旦提交，就不可以回滚
 *4.哪些操作会导致数据的自动提交
 * >DDL操作一旦执行，都会自动提交
 * >DML增删改查默认情况下会自动提交，
 *      我们可以通过set autocommit = false的方式取消DML操作的自动提交
 * >默认关闭连接的时候会自动提交
 */
public class TransactionTest {
    /***************************************未考虑数据库事务操作
     * 针对与数据表user_table来说
     * AA用户给BB用户转帐100
     * update user_table set balance  = balance - 100 where user = 'AA';
     * update user_table set balance  = balance + 100 where user = 'BB';
     */
     @Test
    public void testUpdate(){
        String sql = "update user_table set balance  = balance - 100 where user = ?";
        update(sql,"AA");
        //模拟网络异常
        System.out.println(10/0);

        String sql1 = "update user_table set balance  = balance + 100 where user = ?";
        update(sql1,"BB");

        System.out.println("转账成功");
    }
    //通用的增删改操作 --- version 1.0
    public int update(String sql,Object ...args){
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
        return 0;
    }
    /***************************************考虑数据库事务操作
     * 针对与数据表user_table来说
     * AA用户给BB用户转帐100
     * update user_table set balance  = balance - 100 where user = 'AA';
     * update user_table set balance  = balance + 100 where user = 'BB';
     *
     * alter table user_table engine  = innodb; innddb支持回滚
     * select @@tx_isolation; #可重复度REPEATABLE-READ 默认事务等级
     * 读已提交 READ COMMITED
     */
    @Test
    public void testUpdateWithTx(){
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            //取消数据的自动提交
            conn.setAutoCommit(false);
            String sql = "update user_table set balance  = balance - 100 where user = ?";
            update1(conn,sql,"AA");
            //模拟网络异常
            System.out.println(10/0);
            String sql1 = "update user_table set balance  = balance + 100 where user = ?";
            update1(conn,sql1,"BB");

            System.out.println("转账成功");
            //提交数据
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚数据
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            //修改为自动提交数据
            //主要针对与使用数据连接池的使用
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeConnection(conn,null);
        }
    }
    //通用的增删改操作 --- version 2.0
    public int update1(Connection conn,String sql,Object ...args){
        PreparedStatement ps = null;
        try {
            //args sql中的占位符的个数与可变形参的长度相同
            ps = conn.prepareStatement(sql);
            //填充占位符
            for(int i = 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);//小心参数序号
            }
            //执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //关闭
            JDBCUtils.closeConnection(null, ps);
        }
        return 0;
    }

    //*****************************************************************************************
    @Test
    public void testTransactionSelect() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        //获取当前的隔离级别
        System.out.println(conn.getTransactionIsolation());
        //设置事务隔离级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //取消自动提交数据 ,保证事务没有结束
        conn.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user = ?";
        UserTable  userTable = getInstance(conn,UserTable.class,sql,"CC");
        System.out.println(userTable);
    }
    @Test
    public void testTransactionUpdate(){
        Connection conn = null;
        try {
            //取消自动提交数据 ,保证事务没有结束
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String sql = "update user_table set balance = ? where user = ?";
            update1(conn,sql,5000,"CC");
            Thread.sleep(15000);
            System.out.println("修改结束");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn,null);
        }

    }
    //通用的查询操作，用于返回数据表中的一条数据(version2.0 考虑事务)
    public <T> T getInstance(Connection conn, Class<T> clazz, String sql,Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
