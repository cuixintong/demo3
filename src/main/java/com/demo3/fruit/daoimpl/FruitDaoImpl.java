package com.demo3.fruit.daoimpl;

import com.demo3.fruit.dao.BaseDao;
import com.demo3.fruit.domain.Fruit;

import java.sql.SQLException;
import java.util.List;

public class FruitDaoImpl extends BaseDao implements FruitDao{

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
    public int delete(String fname) throws SQLException {
        String sql = "delete from t_fruit where fname =?;";
        return super.update(sql, fname);
    }

    @Override
    public int update(String fname, double price) throws SQLException {
        String sql = "update t_fruit set price = ? where fname = ?;";
        return super.update(sql, price, fname);
    }

    @Override
    public <T> List<T> findall(Class<T> tClass) throws Exception {
        String sql = "select * from t_fruit";
        return super.query(tClass,sql);
    }
}
