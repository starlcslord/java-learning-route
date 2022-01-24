package com.atguigu.DAOver2;

import com.atguigu.util.JDBCUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装了针对于增删改查
 */
public abstract class BaseDAO<T> {
    private Class<T> clazz = null;

    {
        //获取当前BaseDAO的子类继承的的父类的泛型参数
//        public class CustomerDAOImpl extends BaseDAO<Customer> implements CustomerDAO
//        Type genericSuperclass = this.getClass().getGenericSuperclass();
//        ParameterizedType parameterizedType  = (ParameterizedType) genericSuperclass;
//        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//获取了父类的泛型参数，也就是获取到了BaseDAO<Customer>的Customer
//        clazz =(Class<T>) actualTypeArguments[0];//获取第一个参数，因为就一个参数Customer

        Type type = this.getClass().getGenericSuperclass(); // generic 泛型
        if (type instanceof ParameterizedType) {
            // 强制转化“参数化类型”
            ParameterizedType parameterizedType = (ParameterizedType) type;
            // 参数化类型中可能有多个泛型参数
            Type[] types = parameterizedType.getActualTypeArguments();
            // 获取数据的第一个元素(User.class)
            clazz = (Class<T>) types[0]; // com.oa.shore.entity.User.class
        }
    }
    //通用的查询操作，用于返回数据表中的一条数据(version2.0 考虑事务)
    public  T getInstance(Connection conn, String sql, Object...args){
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
    //通用的增删改操作 --- version 2.0
    public int update(Connection conn,String sql,Object ...args){
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
    /**
     * 针对不同表返回多条记录 V2.0
     */
    public  List<T> getForList(Connection conn, String sql, Object...args){
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
            JDBCUtils.closeConnection(null,ps,rs);
        }
        return null;
    }
    //用于查询特殊值的方法
    public <E> E getValue(Connection conn,String sql,Object...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0 ;i < args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(null,ps,rs);
        }
        return null;
    }
}
