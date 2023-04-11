package com.demo3.fruit.listener;

import com.demo3.fruit.ioc.BeanFactory;
import com.demo3.fruit.ioc.ClassPathXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    public MyServletContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */
        ServletContext servletContext = sce.getServletContext();
        String applictionContextPath = servletContext.getInitParameter("applictionContext");
        BeanFactory beanFactory = new ClassPathXmlApplicationContext(applictionContextPath);
        servletContext.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }


}
