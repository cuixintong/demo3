package com.demo3.fruit.service;

import com.demo3.fruit.daoimpl.FruitDao;
import com.demo3.fruit.daoimpl.FruitDaoImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", value = "/del.do")
public class DeleteServlet extends ViewBaseServlet {

    FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fid1 = request.getParameter("fid");
        int fid = Integer.parseInt(fid1);

        int delete = fruitDao.delete(fid);

        if (delete > 0){
            response.sendRedirect(request.getContextPath() + "/findallfruit");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request, response);
    }
}
