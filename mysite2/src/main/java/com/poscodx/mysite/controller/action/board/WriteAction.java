package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.dao.GuestBookDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("글쓰기~~");
		HttpSession session = request.getSession();
			System.out.println("session: " + session);
	      // Access Control
	      if(session == null) {
	         response.sendRedirect(request.getContextPath());
	         return;
	      }
	      
	      UserVo authUser = (UserVo) session.getAttribute("authUser");
	      if (authUser==null) {
	         response.sendRedirect(request.getContextPath());
	         return;
	      }
	      
		System.out.println("authUser: " + authUser);
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
	
		System.out.println("[param] " + "title:" + title + "   contents: " + content);
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(authUser.getNo());
		new BoardDao().insert(vo);
		

		response.sendRedirect(request.getContextPath() + "/board");
		
	}

}
