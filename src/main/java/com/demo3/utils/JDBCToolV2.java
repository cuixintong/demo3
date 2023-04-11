package com.demo3.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class JDBCToolV2 {

    private static DataSource dataSource = null;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal();

    static {

        Properties properties = new Properties();
        try {


            properties.load(new FileReader(new File("druid.properties")));
//            properties.load(ClassLoader.getSystemResourceAsStream("druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {

        Connection connection = threadLocal.get();

        //如果有就直接返回，没有就新建然后存贷ThreadLocal中
        if (connection == null) {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    public static void free() throws SQLException {
        Connection connection = threadLocal.get();
        if (connection!= null){
            threadLocal.remove();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    public static List<Map> resultToMap(ResultSet resultSet, PreparedStatement preparedStatement) throws SQLException {
        ResultSetMetaData metaData = preparedStatement.getMetaData();
        int columnCount = metaData.getColumnCount();

        ArrayList<Map> maps = new ArrayList<>();
        while (resultSet.next()){
            HashMap<Object, Object> map = new HashMap<>();

            for (int i = 1; i <= columnCount; i++) {
                map.put(metaData.getColumnName(i),resultSet.getObject(i));
            }

            maps.add(map);
        }
        return maps;
    }




}


