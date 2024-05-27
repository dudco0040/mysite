package com.poscodx.mysite.controller.action.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.GuestBookDao;

public class GuestbookAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("list", new GuestBookDao().findAll());
		request
			.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp")
			.forward(request, response);
	}

}
