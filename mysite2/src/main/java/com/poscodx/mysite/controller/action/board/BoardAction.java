package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.UserVo;

public class BoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// for 회원 기능 구분
		HttpSession session = request.getSession();		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		System.out.println("session: " + session);
//		System.out.println("authUser: " + authUser);   // 비회원일 경우, null 
		System.out.println("list");
		
		// 전체 게시물 반환 & 회원 정보
		request.setAttribute("list", new BoardDao().findAll());
		request.setAttribute("authUser", authUser);
		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);
		
	}

}
