package com.demo3.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCToolV1 {

    private static DataSource dataSource = null;

    static {

        Properties properties = new Properties();
        try {
            properties.load(JDBCToolV1.class.getClassLoader().getResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void free(Connection connection) throws SQLException {
        connection.setAutoCommit(true);
        connection.close();
    }




}


