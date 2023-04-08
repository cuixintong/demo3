package com.demo3.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class JDBCUtils {

    private static ThreadLocal<Connection> threadLocal = new ThreadLocal();
    private static DataSource dataSource = null;

    static {
        Properties properties = new Properties();
        InputStream resourceAsStream = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(resourceAsStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(){
        Connection connection = threadLocal.get();
        if (connection == null){
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            threadLocal.set(connection);
        }

        return connection;

    }

    public static void freeConnection() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection != null){
            threadLocal.remove();
            connection.setAutoCommit(true);
            connection.close();
        }


    }


}
