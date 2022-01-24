package com.atguigu.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 此接口用于规范针对与customers表的常用操作
 */
public interface CustomerDAO {
    //添加数据

    /**
     * 将cust对象添加到数据库中
     * @param conn
     * @param cust
     */
    void insert(Connection conn,Customer cust);

    /**
     * 针对指定的id，删除表中的一条记录
     * @param conn
     * @param id
     */
    void deleteById(Connection conn,int id);

    /**
     * 针对内存中的cust对象，去修改数据表中的指定记录
     * @param conn
     * @param cust
     */
    void update(Connection conn,Customer cust);

    /**
     * 根据指定的id查询对象cust
     * @param conn
     * @param id
     */
    Customer getCustomerById(Connection conn,int id);

    /**
     * 查询表中的所有记录
     * @param conn
     * @return
     */
    List<Customer> getAll(Connection conn);
    /**
     * 返回数据表中的条目数
     */
    Long getCount(Connection conn);

    /**
     * 查询最大的生日
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);

}
