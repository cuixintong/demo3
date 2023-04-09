package com.demo3.fruit.service;

import com.demo3.fruit.daoimpl.FruitDaoImpl;
import com.demo3.fruit.domain.Fruit;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/findallfruit")
public class FindAllServlet extends ViewBaseServlet {
        FruitDaoImpl fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer pageno = 1;

        String pagenostr = request.getParameter("pageno");

        if(pagenostr!=null){
            pageno = Integer.parseInt(pagenostr);
        }
        HttpSession session = request.getSession();
        session.setAttribute("pageno", pageno);

        int fruitCount = fruitDao.getFruitCount();

        System.out.println(fruitCount);

        session.setAttribute("pageCount", (fruitCount+5-1)/5);

        List<Fruit> fruits = fruitDao.findall(Fruit.class,pageno);
        fruits.forEach(System.out :: println);
        session.setAttribute("fruitList", fruits);

        //会将逻辑视图对应到物理视图上
        //逻辑视图：index
        //物理视图：view-prefix + 逻辑视图 + view-suffix
        super.processTemplate("index",request,response);//渲染模板




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doGet(request, response);
    }


}
