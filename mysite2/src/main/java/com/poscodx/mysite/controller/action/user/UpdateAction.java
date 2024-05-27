package com.poscodx.mysite.controller.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.UserDao;
import com.poscodx.mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// Access Control
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		String name = request.getParameter("name");
//		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		System.out.println("(update param) name:" + name + " pass: " + password + " gender: "+ gender);
		
		
		UserVo vo = new UserVo();
		vo.setNo(authUser.getNo());
		vo.setName(name);
//		vo.setEmail(email);
		vo.setPassword(password);
		vo.setGender(gender);
		System.out.println(vo);
		
		new UserDao().update(vo);

		response.sendRedirect(request.getContextPath()+"/user?a=updateform");

		
	}

}
