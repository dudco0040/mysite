package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Access Control
		HttpSession session = request.getSession();
		// System.out.println("session: " + session);
		
		if(session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		System.out.println("authUser" + authUser);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		
		// 화면 이동 없이 SQL 만 실행
		String no = request.getParameter("no");
		// System.out.println("delete [" + no + "]");
		new BoardDao().delete(Long.parseLong(no), authUser.getNo());
		
		// redirection
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
