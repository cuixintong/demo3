package com.demo3.fruit.dao;

import com.demo3.utils.JDBCUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao {

    /**
     * 增 删 改
     * */
    public int update(String sql,Object... params) throws SQLException {

        Connection connection = JDBCUtils.getConnection();

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
     * 查一
     * **/
    public <T> T queryOne(Class<T> tClass,String sql,int fid) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Connection connection = JDBCUtils.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,fid);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            Constructor<T> declaredConstructor = tClass.getDeclaredConstructor();
            T t = declaredConstructor.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object string = resultSet.getObject(i);

                Field declaredField = t.getClass().getDeclaredField(columnName);
                declaredField.setAccessible(true);
                declaredField.set(t,string);

            }

            resultSet.close();
            preparedStatement.close();
            if (connection.getAutoCommit()){
                JDBCUtils.freeConnection();
            }

            return t;
        }
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()){
            JDBCUtils.freeConnection();
        }

        return null;
    }

    /**
     * 查全部
     * **/
    public <T> List<T> queryAll(Class<T> tClass,String sql,Object... params) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        Connection connection = JDBCUtils.getConnection();

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
