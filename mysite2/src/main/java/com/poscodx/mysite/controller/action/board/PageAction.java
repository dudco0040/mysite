package com.poscodx.mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.dao.BoardDao;
import com.poscodx.mysite.vo.BoardVo;

public class PageAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // 현재 페이지 번호 받아오기 (default: 1)
	    int currentPage = 1;
	    if (request.getParameter("page") != null) {
	        currentPage = Integer.parseInt(request.getParameter("page"));
	    }

	    int recordsPerPage = 8; // 한 페이지당 보여줄 글의 개수
	    BoardDao boardDao = new BoardDao();
	    int totalRecords = boardDao.findAll().size(); // 전체 글의 개수 가져오기
	    System.out.println(totalRecords);

	    List<BoardVo> list = boardDao.getList((currentPage - 1) * recordsPerPage, recordsPerPage);

	    int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);

	    request.setAttribute("list", list);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("totalPages", totalPages);

	    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
	    rd.forward(request, response);

	}

}
