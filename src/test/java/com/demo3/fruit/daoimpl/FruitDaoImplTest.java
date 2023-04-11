package com.demo3.fruit.daoimpl;

import com.demo3.fruit.domain.Fruit;
import org.junit.Test;

import java.sql.SQLException;

public class FruitDaoImplTest {

    @Test
    public void test() throws SQLException {
        FruitDaoImpl fruitDao = new FruitDaoImpl();

        Fruit orange = new Fruit(1, "ggagaga", 12, 32, null);

            int add = fruitDao.add(orange);


    }



}