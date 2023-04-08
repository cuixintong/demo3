package com.demo3.fruit.dao;

import com.demo3.utils.JDBCUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao {

    Connection connection = JDBCUtils.getConnection();

    /**
     * 增 删 改
     * */
    public int update(String sql,Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= params.length; i++) {
            preparedStatement.setObject(i, params[i-1]);
        }

        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        if (connection.getAutoCommit()){
            JDBCUtils.freeConnection();
        }

        return i;

    }

    /**
     * 查
     * **/
    public <T> List<T> query(Class<T> tClass,String sql,Object... params) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 1; i <= params.length; i++) {
            preparedStatement.setObject(i,params[i-1]);
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        ArrayList<T> ts = new ArrayList<>();
        while (resultSet.next()){
            Constructor<T> cons = tClass.getDeclaredConstructor();
            T t = cons.newInstance();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object string = resultSet.getObject(i);
                Field declaredField = t.getClass().getDeclaredField(columnName);
                declaredField.setAccessible(true);
                declaredField.set(t,string);
            }
            ts.add(t);
        }

        resultSet.close();
        preparedStatement.close();

        if (connection.getAutoCommit()){
            JDBCUtils.freeConnection();
        }

        return ts;

    }
}
