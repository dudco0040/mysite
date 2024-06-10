package com.poscodx.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler) throws Exception {
		
		System.out.println("authInterceptor");
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// DefaultServletHandler가 처리하는 경우(정적 자원, /assets/**, mapping이 안되어 있는 URL)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. handler Method의 @Auth 가져오기 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. handler Method에 @Auth가 없는 경우  - 회원이 아님
		if(auth == null) {
			return true;
		}
		
		//5. @Auth가 있는 경우는 인증(Authentication) 확인
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		System.out.println(authUser);
		
		// @Auth가 있지만 인증이 안된 경우
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		//6. @Auth가 있고, 인증도 완료된 경우 
		return true;
	
	}

}
