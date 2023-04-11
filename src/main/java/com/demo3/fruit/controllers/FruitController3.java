package com.demo3.fruit.controllers;

import com.demo3.fruit.daoimpl.FruitDaoImpl;
import com.demo3.fruit.domain.Fruit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class FruitController3 {
    FruitDaoImpl fruitDao = new FruitDaoImpl();

    private String index(String oper,String keyword,Integer pageno,HttpServletRequest request) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        if (pageno == null){
            pageno = 1;
        }
        System.out.println("Controller的index");

        HttpSession session = request.getSession();

        System.out.println(oper);

        if ("search".equals(oper)) {
            pageno = 1;
            System.out.println(keyword);
            if (keyword == null){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);

        }else {
            keyword = (String) session.getAttribute("keyword");
            if (keyword == null){
                keyword = "";
            }
        }

        session.setAttribute("pageno", pageno);

        int fruitCount = fruitDao.getFruitCount(keyword);

        System.out.println(fruitCount);

        session.setAttribute("pageCount", (fruitCount+5-1)/5);

        List<Fruit> fruits = fruitDao.findall(Fruit.class,pageno,keyword);
        fruits.forEach(System.out :: println);
        session.setAttribute("fruitList", fruits);

        //会将逻辑视图对应到物理视图上
        //逻辑视图：index
        //物理视图：view-prefix + 逻辑视图 + view-suffix
        return "index";//渲染模板

    }

    private String addrender(HttpServletRequest request) {
        return "add";
    }

    private String add(String fname,double price,Integer fcount,String remark) throws SQLException {

        Fruit fruit = new Fruit(1, fname, price, fcount, remark);
        System.out.println(fruit);
        int add = fruitDao.add(fruit);

        if (add > 0){
            return "redirect:fruit.do";
        }

        return "redirect:/error.do";


    }

    private String delete(Integer fid,Integer pageno) throws SQLException {

        System.out.println("pageno"+pageno);

        int delete = fruitDao.delete(fid);

        if (delete > 0){
            return "redirect:/fruit.do?pageno="+pageno;
        }

        return "redirect:/error.do";

    }

    private String edit(Integer fid,HttpServletRequest request) throws SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {



        Fruit fruit = fruitDao.findOne(fid);

        request.setAttribute("fruit", fruit);

        return "edit";


    }

    private String update(Integer fid,String fname,double price,Integer fcount,String remark) throws SQLException {
        Fruit fruit = new Fruit(fid, fname, price, fcount, remark);
        System.out.println(fruit);
        int update = fruitDao.update(fruit);
        if (update > 0) {
            return "redirect:/fruit.do";
        }

        return "redirect:/error.do";

    }
}
