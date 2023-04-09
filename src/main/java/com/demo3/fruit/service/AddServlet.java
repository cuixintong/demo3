package com.demo3.fruit.service;

import com.demo3.fruit.daoimpl.FruitDaoImpl;
import com.demo3.fruit.domain.Fruit;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/add.do")
public class AddServlet extends HttpServlet {

    FruitDaoImpl fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            response.sendRedirect(request.getContextPath() + "/findallfruit");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("111");

        this.doGet(request, response);
    }
}
