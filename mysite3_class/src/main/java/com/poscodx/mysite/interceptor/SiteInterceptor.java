package com.poscodx.mysite.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private SiteService siteService;
	
	public SiteInterceptor(SiteService siteService) {
		this.siteService = siteService;
	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1번 구현 @auth 인식
		SiteVo siteVo = (SiteVo) request.getServletContext().getAttribute("sitevo");
		
		if(siteVo == null) {
			
			siteVo = siteService.getProfile();
			request.getServletContext().setAttribute("sitevo", siteVo);
		}
		
		System.out.println(siteVo);
				
//		request
//			.getRequestDispatcher("WEB-INF/views/includes/header.jsp")
//			.forward(request, response);
		
		return true;
	}

}
