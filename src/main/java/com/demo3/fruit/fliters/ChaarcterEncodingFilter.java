package com.demo3.fruit.fliters;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebFilter(filterName = "ChaarcterEncodingFilter",urlPatterns = {"*.do"},initParams = {@WebInitParam(name = "encoding",value = "UTF-8")})
public class ChaarcterEncodingFilter implements Filter {

    private String encoding = "UTF-8";
    public void init(FilterConfig config) throws ServletException {
        String encodingstr = config.getInitParameter("encoding");
        if (encodingstr != null){
            encoding = encodingstr;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
