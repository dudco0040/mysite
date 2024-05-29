package com.poscodx.mysite.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ConTextLoadListener implements ServletContextListener {


    public void contextInitialized(ServletContextEvent sce)  {
    	ServletContext sc = sce.getServletContext();  // 서블릿 컨텍스트 가져오기
    	String contextConfigLoaction = sc.getInitParameter("contextConfigLoaction");
    	
    	
    	System.out.println("Application[site2] start.." + contextConfigLoaction);
   }
    
    public void contextDestroyed(ServletContextEvent sce)  { 
        
    }
	
}
