package com.demo3.fruit.daoimpl;

import com.demo3.fruit.dao.BaseDao;
import com.demo3.fruit.domain.Fruit;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class FruitDaoImpl extends BaseDao implements FruitDao {

    @Override
    public int add(Fruit fruit) throws SQLException {
        String fname = fruit.getFname();
        double price = fruit.getPrice();
        int fcount = fruit.getFcount();
        String remark = fruit.getRemark();

        String sql = "insert into t_fruit values (null,?,?,?,?);";

        return  super.update(sql, fname, price, fcount, remark);

    }

    @Override
    public int delete(int fid) throws SQLException {
        String sql = "delete from t_fruit where fid =?;";
        return super.update(sql, fid);

    }

    @Override
    public int update(Fruit fruit) throws SQLException {
        String sql = "update t_fruit set fname = ?, price = ? , fcount = ? , remark = ? where fid = ?;";
        return super.update(sql, fruit.getFname(),fruit.getPrice(), fruit.getFcount(),fruit.getRemark(),fruit.getFid());

    }

    @Override
    public Fruit findOne(Integer fid) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from t_fruit where fid =?;";
        return super.queryOne(Fruit.class,sql,fid);

    }

    @Override
    public <T> List<T> findall(Class<T> tClass,int pageno,String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from t_fruit where fname like ? or remark like ? limit ? , 5;";
        return super.queryAll(tClass,sql,"%"+keyword+"%","%"+keyword+"%",(pageno-1)*5);

    }

    @Override
    public <T> List<T> findall(Class<T> tClass,String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String sql = "select * from t_fruit where fname like ? or remark like ?;";
        return super.queryAll(tClass,sql,"%"+keyword+"%","%"+keyword+"%");

    }

    @Override
    public int getFruitCount(String keyword) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<Fruit> findall = findall(Fruit.class,keyword);
        return findall.size();
    }
}
