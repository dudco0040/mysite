package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");	
		System.out.println("[modifyform]" + no + ", " +  title + ", "+ contents);
		
		// 수정 form 에서 기존의 title, contents 값이 보이도록 받음 - 필요 없다면 안받아도 됨 (hidden 필요)
		request.setAttribute("no", no);
		request.setAttribute("title", title);
		request.setAttribute("contents", contents);
		
		
		request
		.getRequestDispatcher("/WEB-INF/views/board/modify.jsp")
		.forward(request, response);

	}

}
