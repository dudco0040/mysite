package com.poscodx.mysite.controller.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo authUser = new UserDao().findByNoAndPassword(email, password);
		if(authUser == null) {
			// id, password가 틀린 경우
			// 틀렸다고 하지 않고 없는 사용자라고 알려주기 - 보안 상의 문제가 생길 수 있음 
			request.setAttribute("email", email);
			request.setAttribute("result", "fail");
			request
				.getRequestDispatcher("/WEB-INF/views/user/loginform.jsp")
				.forward(request, response);
			
			return; 
		}
		
		// login 처리 
		HttpSession session = request.getSession(true);   // JSID 추출? true: 없으면 생성 -> false: 없으면 만들지 않음(our)
		session.setAttribute("authUser", authUser);
		
		// login 후 메인 화면으로 리다이렉트
		response.sendRedirect(request.getContextPath());
	}

}
