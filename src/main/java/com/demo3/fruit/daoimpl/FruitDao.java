package com.demo3.fruit.daoimpl;

import com.demo3.fruit.domain.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface FruitDao {

    //增
    public int add(Fruit fruit);

    //删
    public int delete(int fid);

    //改
    public int update(String fname, double price,int fconut,String remark) throws SQLException;

    //查一
    public <T> T findOne(Class<T> tClass,int fid) throws Exception;

    //查全部
    public <T> List<T> findall(Class<T> tClass,int pageno) throws Exception;

    public <T> List<T> findall(Class<T> tClass);
    public int getFruitCount();

}
