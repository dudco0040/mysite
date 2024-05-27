package com.poscodx.mysite.controller.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();  // JSID 가져오기 
		System.out.println("session: " + session.getId());
		// 접근제어(Access Control)
		if(session == null) {  // 로그인을 하지 않고 들어올 경우 - 세션이 존재하지 않으면 main으로 리다이렉션
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		// 사용자 정보 가져오기
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		System.out.println("authUser: " +authUser.getName());
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		//////////////////////////////////////////////////////////////////////////

		UserVo userVo = new UserDao().findByNo(authUser.getNo());
		System.out.println("(updateFormAction)" + userVo);
		request.setAttribute("userVo", userVo);
		request.getRequestDispatcher("/WEB-INF/views/user/updateform.jsp")
		.forward(request, response);
	}

}
