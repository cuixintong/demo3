package com.demo3.fruit.daoimpl;

import com.demo3.fruit.dao.BaseDao;
import com.demo3.fruit.domain.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class FruitDaoImpl extends BaseDao implements FruitDao{

    @Override
    public int add(Fruit fruit){
        String fname = fruit.getFname();
        double price = fruit.getPrice();
        int fcount = fruit.getFcount();
        String remark = fruit.getRemark();

        String sql = "insert into t_fruit values (null,?,?,?,?);";

        try {
            return  super.update(sql, fname, price, fcount, remark);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public int delete(int fid) {
        String sql = "delete from t_fruit where fid =?;";
        try {
            return super.update(sql, fid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(String fname, double price,int fcount,String remark) {
        String sql = "update t_fruit set price = ? , fcount = ? , remark = ? where fname = ?;";
        try {
            return super.update(sql, price, fcount,remark,fname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T findOne(Class<T> tClass,int fid) {
        String sql = "select * from t_fruit where fid =?;";
        try {
            return super.queryOne(tClass,sql,fid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findall(Class<T> tClass,int pageno) {
        String sql = "select * from t_fruit limit ? , 5;";
        try {
            return super.queryAll(tClass,sql,(pageno-1)*5);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findall(Class<T> tClass) {
        String sql = "select * from t_fruit;";
        try {
            return super.queryAll(tClass,sql);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getFruitCount(){
        List<Fruit> findall = findall(Fruit.class);
        return findall.size();
    }
}
