package com.demo3.fruit.servlet;

import com.demo3.fruit.ioc.BeanFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@WebServlet(name = "DispatcherServlet", value = "*.do")
public class DispatcherServlet extends ViewBaseServlet {

    private BeanFactory beanFactory;

    public void init() throws ServletException {
        super.init();
//        beanFactory = new ClassPathXmlApplicationContext();
        Object beanFactory1 = getServletContext().getAttribute("beanFactory");
        if (beanFactory1!= null) {
            this.beanFactory = (BeanFactory) beanFactory1;
        }else {
            throw new IOCRunTimeException("IOC容器创建失败");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");

        String servletPath = request.getServletPath();//*.do
        System.out.println(servletPath);

        servletPath = servletPath.substring(1);
        int i = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, i);

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = request.getParameter("operate");
        if (operate == null) {
            operate = "index";
        }

        try {
//            Class<?> fruitServlet = Class.forName("com.demo3.fruit.service.FruitServlet");
            Method[] declaredMethods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method m : declaredMethods) {
                if (operate.equals(m.getName())) {
                    //1,统一获取参数
                    Parameter[] parameters = m.getParameters();
                    Object[] parameterValues = new Object[parameters.length];
                    for (int j = 0; j < parameters.length; j++) {
                        Parameter parameter = parameters[j];
                        String name = parameter.getName();
                        if ("request".equals(name)) {
                            parameterValues[j] = request;
                        } else if ("response".equals(name)) {
                            parameterValues[j] = response;
                        } else if ("session".equals(name)) {
                            parameterValues[j] = request.getSession();
                        } else {
                            String parameterValue = request.getParameter(name);
                            String typeName = parameter.getType().getName();
                            if (parameterValue != null) {
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterValues[j] = Integer.parseInt(parameterValue);
                                } else if ("double".equals(typeName)) {
                                    parameterValues[j] = Double.parseDouble(parameterValue);
                                } else {
                                    parameterValues[j] = parameterValue;//这里获取的pageno=“2”  并不是2
                                }
                            }
                        }
                    }
                    String methodReturnStr = null;
                    //2,controller组件中的方法调用
                    m.setAccessible(true);
                    Object o = m.invoke(controllerBeanObj, parameterValues);//会报错参数不匹配
                    if (o != null) {
                        methodReturnStr = (String) o;
                    }
                    //3，提取视图资源处理
                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        System.out.println(request.getContextPath() + redirectStr);
                        response.sendRedirect(request.getContextPath() + redirectStr);

                    } else {
                        super.processTemplate(methodReturnStr, request, response);
                    }
                }
            }
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
//        }else {
//                throw new RuntimeException("方法错误");
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
