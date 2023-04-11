package com.demo3.fruit.fliters;

import com.demo3.fruit.tran.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@WebFilter(filterName = "OpenSessionInViewFilter",urlPatterns = {"*.do"})
public class OpenSessionInViewFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            TransactionManager.startTransaction();
            chain.doFilter(request, response);
            TransactionManager.commitTransaction();
        } catch (Exception e) {
            try {
                TransactionManager.rollbackTransaction();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        try {
            TransactionManager.closeTransaction();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
    }
}
