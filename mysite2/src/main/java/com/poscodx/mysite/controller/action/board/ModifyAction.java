package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class ModifyAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		// Access Control
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
		
		BoardVo vo = new BoardVo();
		request.setAttribute("vo", vo);

		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String no = request.getParameter("no"); 
		System.out.println("(update param) title:" + title + " contents: " + content);

		vo.setTitle(title);
		vo.setContents(content);
		vo.setNo(Long.parseLong(no));
		vo.setUserNo(authUser.getNo());  // 본인만 수정 가능해야 함 
		
		// update
		new BoardDao().update(vo);

		response.sendRedirect(request.getContextPath()+"/board");
		
	}

}
