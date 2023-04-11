package com.demo3.fruit.service.impl;

import com.demo3.fruit.service.FruitService;
import com.demo3.fruit.daoimpl.FruitDao;
import com.demo3.fruit.domain.Fruit;
import com.demo3.utils.JDBCUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class FruitServiceImpl implements FruitService {

    private FruitDao fruitDao = null;
    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageno) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(JDBCUtils.getConnection());
        return fruitDao.findall(Fruit.class,pageno,keyword);
    }

    @Override
    public Integer addFruit(Fruit fruit) throws SQLException {
        return fruitDao.add(fruit);
    }

    @Override
    public Integer updateFruit(Fruit fruit) throws SQLException {
        return fruitDao.update(fruit);
    }

    @Override
    public Integer deleteFruit(Integer fid) throws SQLException {
        return fruitDao.delete(fid);
    }

    @Override
    public Fruit getFruitById(Integer fid) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return fruitDao.findOne(fid);
    }

    @Override
    public Integer getPageCount(String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        System.out.println(JDBCUtils.getConnection());
        return (fruitDao.getFruitCount(keyword) + 5 - 1) / 5;
    }
}
