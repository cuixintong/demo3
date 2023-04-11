package com.demo3.fruit.service;

import com.demo3.fruit.domain.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface FruitService {

    //获取水果列表
    public List<Fruit> getFruitList(String keyword,Integer pageno) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public Integer addFruit(Fruit fruit) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public Integer updateFruit(Fruit fruit) throws SQLException;

    public Integer deleteFruit(Integer id) throws SQLException;

    public Fruit getFruitById(Integer id) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    public Integer getPageCount(String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
