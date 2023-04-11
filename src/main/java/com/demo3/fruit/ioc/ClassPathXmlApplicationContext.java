package com.demo3.fruit.ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory{

    private Map<String,Object> beanMap = new HashMap<String,Object>();
    private String path = "applicationContext.xml";

    public ClassPathXmlApplicationContext() {
        this("applicationContext.xml");
    }

    public ClassPathXmlApplicationContext(String path){
        if (path == null){
            throw new IllegalArgumentException("path can not be");
        }
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(path);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(resourceAsStream);

            NodeList beans = document.getElementsByTagName("bean");
            for (int i = 0; i < beans.getLength(); i++) {
                Node item = beans.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) item;
                    String id = element.getAttribute("id");
                    String aClass = element.getAttribute("class");
                    Object beanObj = Class.forName(aClass).getDeclaredConstructor().newInstance();
                    beanMap.put(id,beanObj);
                }
            }

            for (int i = 0; i < beans.getLength(); i++) {
                Node item = beans.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element)item;
                    NodeList childNodes = element.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node childNode = childNodes.item(j);
                        if (childNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(childNode.getNodeName())){
                            Element beanChildNode = (Element) childNode;
                            String name = beanChildNode.getAttribute("name");
                            String ref = beanChildNode.getAttribute("ref");

                            Object refObj = beanMap.get(ref);
                            String beanid = element.getAttribute("id");
                            Object beanObj = beanMap.get(beanid);

                            Field propertyField = beanObj.getClass().getDeclaredField(name);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj,refObj);
                        }
                    }
                }
            }
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public Object getBean(String id) {

        return beanMap.get(id);

    }
}
