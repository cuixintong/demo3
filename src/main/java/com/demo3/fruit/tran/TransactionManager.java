package com.demo3.fruit.tran;

import com.demo3.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {

    public static void startTransaction() throws SQLException {
        Connection connection = JDBCUtils.getConnection();
        connection.setAutoCommit(false);
        System.out.println("开启事务");
    }

    public static void commitTransaction() throws SQLException {
        JDBCUtils.getConnection().commit();
        System.out.println("提交事务");
    }

    public static void rollbackTransaction() throws SQLException {
        JDBCUtils.getConnection().rollback();
        JDBCUtils.freeConnection();
        System.out.println("回滚事务");
    }

    public static void closeTransaction() throws SQLException {
        JDBCUtils.freeConnection();
        System.out.println("关闭事务");
    }
}
