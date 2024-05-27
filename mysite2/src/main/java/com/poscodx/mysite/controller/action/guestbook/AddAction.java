package com.poscodx.mysite.controller.action.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.GuestBookVo;

public class AddAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String Name = request.getParameter("name");
		String Password = request.getParameter("password");
		String Contents = request.getParameter("content");
		
		GuestBookVo vo = new GuestBookVo();
		vo.setName(Name);
		vo.setPassword(Password);
		vo.setContents(Contents);
		new GuestBookDao().insert(vo);
		
		response.sendRedirect(request.getContextPath() + "/guestbook");

	}

}
