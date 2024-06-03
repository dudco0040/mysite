package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	// 요청과 매핑이 필요 - mysite3 까지 치면 /를 추가하여 redirection
	@RequestMapping({"/", "/main"})
	public String index(HttpServletRequest request){
	
		ServletContext sc = request.getServletContext();
		
		// 이름 알아내기 
//		Enumeration<String> names = (Enumeration<String>)sc.getAttributeNames();
//		
//		while(names.hasMoreElements()) {
//			String name = names.nextElement();
//			System.out.println(name);
//		}
		
		ApplicationContext ac1 = (ApplicationContext)sc.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
		ApplicationContext ac2 = (ApplicationContext)sc.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher-servlet");
		ApplicationContext ac3 = (ApplicationContext)sc.getAttribute("org.springframework.web.servlet.FrameworkServlet.CONTEXT.dispatcher-servlet");
		
		System.out.println(ac1);
		System.out.println(ac2);
		
		return "main/index";
	}

}
