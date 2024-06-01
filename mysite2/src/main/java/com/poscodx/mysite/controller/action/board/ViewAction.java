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
		// 회원 확인
		HttpSession session = request.getSession();		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		// 조회수 기능 추가 글 번호 비교해서 hit+=1
		String no = request.getParameter("no");
		new BoardDao().updateHit(Long.parseLong(no));
		// System.out.println("++조회수");
		
		request.setAttribute("authUser", authUser);
		request.setAttribute("vo", new BoardDao().findByTitleAndUsernoView(Long.parseLong(no)));  // Title(글 제목), User Name(글쓴이)
		request
			.getRequestDispatcher("/WEB-INF/views/board/view.jsp")
			.forward(request, response);
	}
}
