package com.demo3.fruit.service;

import com.demo3.fruit.daoimpl.FruitDaoImpl;
import com.demo3.fruit.domain.Fruit;
import com.demo3.utils.JDBCUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet(name = "EditServlet", value = "/edit.do")
public class EditServlet extends ViewBaseServlet {

    FruitDaoImpl fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setCharacterEncoding("UTF-8");

        int fid = Integer.parseInt(request.getParameter("fid"));
        Fruit fruit = fruitDao.findOne(Fruit.class, fid);

        request.setAttribute("fruit", fruit);

        processTemplate("edit", request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
        int update = fruitDao.update(fname, price, fcount, remark);
        if (update > 0) {
            response.sendRedirect(request.getContextPath() + "/findallfruit");
        }


    }
}
