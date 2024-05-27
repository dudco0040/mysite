package com.poscodx.mysite.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected abstract Action getAction(String actionName);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String actionName = Optional.ofNullable(req.getParameter("a")).orElse("");
		
		// action이 없거나 알 수 없는 경우 
		Action action = getAction(actionName);  // action을 요청
		if(action == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		action.execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

	public static interface Action {
		void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	}
}


//public abstract class ActionServlet extends HttpServlet {
//	private static final long serialVersionUID =1L;
//
//	protected abstract Action getAction(String actionName);
//	
//	@Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		String actionName = Optional.ofNullable(request.getParameter("a")).orElse("");  // null이 아니면 원래 값을 저장, null 이면 ""을 넣어줌 - null은 빈 값을 확인하는 방법일 뿐
//		
//		// action이 없거나 알 수 없는 경우 
//		Action action = getAction(actionName);
//		if(action == null) {
//			request.sendError(HttpServletResponse.SC_BAD_REQUEST);  // 404 Error?
//			return; 
//		}
//		
//		action.execute(request, response);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		super.doPost(request, response);
//	}
//	
//	public static interface Action{
//		void execute(HttpServletRequest request, HttpServletResponse response);
//	}
//
//}
