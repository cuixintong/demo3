package com.demo3.fruit.controllers;

import com.demo3.fruit.daoimpl.FruitDaoImpl;
import com.demo3.fruit.domain.Fruit;
import com.demo3.fruit.servlet.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

public class FruitController2 extends ViewBaseServlet {
    FruitDaoImpl fruitDao = new FruitDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String operate = request.getParameter("operate");
        if (operate == null){
            operate = "index";
        }

        try {
//            Class<?> fruitServlet = Class.forName("com.demo3.fruit.service.FruitServlet");
            Class<? extends FruitController2> aClass = this.getClass();
            Method declaredMethod = aClass.getDeclaredMethod(operate,HttpServletRequest.class,HttpServletResponse.class);
            declaredMethod.setAccessible(true);

            declaredMethod.invoke(this, request,response);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        用Switch case
//        request.setCharacterEncoding("utf-8");
//
//        String operate = request.getParameter("operate");
//
//        if (operate == null){
//            operate = "index";
//        }
//
//        switch (operate){
//            case "index":
//                this.index(request, response);
//                break;
//
//            case "addrender":
//                this.addrender(request, response);
//                break;
//
//            case "add":
//                this.add(request, response);
//                break;
//
//            case "delete":
//                this.delete(request, response);
//                break;
//
//            case "edit":
//                this.edit(request, response);
//                break;
//
//            case "update":
//                this.update(request, response);
//                break;
//
//            default:
//                throw new RuntimeException("opreate值非法！");
//        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        this.doGet(request,response);
    }

    private String index(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        request.setCharacterEncoding("UTF-8");
        Integer pageno = 1;
        HttpSession session = request.getSession();

        String keyword = null;

        String search = request.getParameter("oper");
        System.out.println(search);

        if ("search".equals(search)) {
            pageno = 1;
            keyword = request.getParameter("keyword");
            System.out.println(keyword);
            if (keyword == null){
                keyword = "";
            }
            session.setAttribute("keyword",keyword);

        }else {
            String pagenostr = request.getParameter("pageno");

            if(pagenostr!=null){
                pageno = Integer.parseInt(pagenostr);
            }
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

    private String addrender(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        return "add";
    }

    private String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        request.setCharacterEncoding("utf-8");

        String fname = request.getParameter("fname");
        System.out.println(fname);
        String price1 = request.getParameter("price");
        double price = Double.parseDouble(price1);
        String fcount1 = request.getParameter("fcount");
        int fcount = Integer.parseInt(fcount1);
        String remark = request.getParameter("remark");

        Fruit fruit = new Fruit(1, fname, price, fcount, remark);
        System.out.println(fruit);
        int add = fruitDao.add(fruit);

        if (add > 0){
            return "redirect:fruit.do";
        }

        return "redirect:/error.do";


    }

    private String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        request.setCharacterEncoding("UTF-8");

        String fid1 = request.getParameter("fid");
        String pageno1 = request.getParameter("pageno");
        int pageno = Integer.parseInt(pageno1);
        System.out.println("pageno"+pageno);
        int fid = Integer.parseInt(fid1);

        int delete = fruitDao.delete(fid);

        if (delete > 0){
            return "redirect:/fruit.do?pageno="+pageno;
        }

        return "redirect:/error.do";

    }

    private String edit(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NoSuchFieldException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {


        request.setCharacterEncoding("UTF-8");

        int fid = Integer.parseInt(request.getParameter("fid"));
        Fruit fruit = fruitDao.findOne(fid);

        request.setAttribute("fruit", fruit);

        return "edit";


    }

    private String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        request.setCharacterEncoding("UTF-8");
        String fid1 = request.getParameter("fid");
        int fid = Integer.parseInt(fid1);
        String fname = request.getParameter("fname");
        String price1 = request.getParameter("price");
        double price = Double.parseDouble(price1);
        String fcount1 = request.getParameter("fcount");
        int fcount = Integer.parseInt(fcount1);
        String remark = request.getParameter("remark");

        Fruit fruit = new Fruit(fid, fname, price, fcount, remark);
        int update = fruitDao.update(fruit);
        if (update > 0) {
            return "redirect:/fruit.do";
        }

        return "redirect:/error.do";


    }
}
