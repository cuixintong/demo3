package com.demo3.fruit.daoimpl;

import com.demo3.fruit.domain.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface FruitDao {

    //增
    public int add(Fruit fruit) throws SQLException;

    //删
    public int delete(String fname) throws SQLException;

    //改
    public int update(String fname, double price) throws SQLException;

    //查
    public <T> List<T> findall(Class<T> tClass) throws Exception;


}
