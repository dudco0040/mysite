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
		
		//1. handler 종류 확인
		if(!(handler instanceof HandlerMethod)) {
			// 검사가 필요하지 않는 경우(asset, login, logout)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method의 @Auth 가져오기 
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//4. Handler Method에 @Auth가 없는 경우
		// To do - Handler Method에 @Auth가 없으면 Type(Class)에 붙어있는지 확인
		// 관리자 페이지에 관리자만 접속 가능해야 함

		if(auth == null) {
			auth = handlerMethod
					.getMethod()
					.getDeclaringClass()
					.getAnnotation(Auth.class);
		}
		
		if(auth == null) {
			return true;
		}
		
		
		
		//5. @Auth가 붙어있기 때문에 인증 여부 확인 -> Type이나 Method에 @Auth가 없는 경우 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");  // 비정상적인 접근일 경우, login 화면으로 리다이렉트
			return false;
		}
		
		//6. 권한 체크 ! 인증은 완료 - 권한 확인을 위해 @Auth의 role 가져오기("USER", "ADMIN") 
		String role = auth.role();  // 회원의 권한을 가져오기
		
		
		//7. 로그인한 사용자(@Auth)가 UESR/ ADMIN 상관없음 - ex) update같은 영역 
		if("USER".equals(role)) {
			return true;
		}
		
		//8. ADMIN 권한이 아닌 경우
		if(!"ADMIN".equals(authUser.getRole())) {  // 로그인 한 사용자의 권한을 비교 
			response.sendRedirect(request.getContextPath());
			return false; // 핸들러 실행을 막음
		} // admin 권한 페이지에 비정상적인 접근을 할 경우 방지 
				
		
		//9.  ADMIN 권한인 경우, @Auth(role="ADMIN"), authUser.getRole() == "ADMIN"
		
		return true;
		
	}

}
