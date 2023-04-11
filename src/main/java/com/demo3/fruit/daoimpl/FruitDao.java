package com.demo3.fruit.daoimpl;

import com.demo3.fruit.domain.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface FruitDao {

    //增
    public int add(Fruit fruit) throws SQLException;

    //删
    public int delete(int fid) throws SQLException;

    //改
    public int update(Fruit fruit) throws SQLException;

    //查一
    public Fruit findOne(Integer fid) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    //查全部
    public <T> List<T> findall(Class<T> tClass,int pageno,String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public <T> List<T> findall(Class<T> tClass,String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
    public int getFruitCount(String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}
