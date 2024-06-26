package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

public class BoardAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// for 회원 기능 구분
		HttpSession session = request.getSession();		
		UserVo authUser = (UserVo)session.getAttribute("authUser");  // 비회원일 경우, null  
		System.out.println("list");
	
		
		// for 페이지 구현
		// 현재 페이지 번호 받아오기
	    int currentPage = 1;  // 기본 페이지는 1부터(default)
	    if (request.getParameter("page") != null) {
	        currentPage = Integer.parseInt(request.getParameter("page"));
	    }
	    
	    // 한 페이지당 보여줄 글의 개수
	    int recordsPerPage = 8;
	    // 전체 글의 개수 
	    BoardDao boardDao = new BoardDao();
	    int totalRecords = boardDao.countRecords();
	    // System.out.println(totalRecords);
	    // 글 목록 - 전체 페이지 수
	    List<BoardVo> list = boardDao.getList((currentPage - 1) * recordsPerPage, recordsPerPage);
	    int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
	    
		// 전체 게시물 반환 & 회원 정보
//		request.setAttribute("list", boardDao.findAll());  // getList()로 새로 구현
		request.setAttribute("authUser", authUser);
		
		// 페이지 구현을 위한 변수 전달 (글 목록, 현재 페이지, 전체 페이지 수)
		request.setAttribute("list", list);
		request.setAttribute("currentPage", currentPage);
	    request.setAttribute("totalPages", totalPages);
		

		request
			.getRequestDispatcher("/WEB-INF/views/board/list.jsp")
			.forward(request, response);	
	}

}
