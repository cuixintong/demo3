package com.demo3.fruit.fliters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter("/fruit.do")
public class DemoFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("DemoFilter before");
        chain.doFilter(request, response);
        System.out.println("DemoFilter after");
    }

    public void destroy() {
    }
}
