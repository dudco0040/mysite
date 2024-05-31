package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;

public class ViewAction implements Action{
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session
		HttpSession session = request.getSession();		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		System.out.println("session: " + session);
//		System.out.println("authUser: " + authUser);
		
		request.setAttribute("authUser", authUser);
		String no = request.getParameter("no");
		request.setAttribute("vo", new BoardDao().findByTitleAndUserno(Long.parseLong(no)));  // Title(글 제목), User Name(글쓴이)
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
	}
}
