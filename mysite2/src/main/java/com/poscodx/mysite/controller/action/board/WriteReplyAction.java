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

public class WriteReplyAction implements Action {
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("답글쓰기~~"); 
		String reply = request.getParameter("reply");
		String no = request.getParameter("no");  // 답글 다려고 하는 본문의 번호 안들어옴 
		System.out.println("***reply:  " + reply + "   no: " + no);
		
		// 글 번호를 기준으로 본문의 정보를 가져와서 vo에 저장(g_no, o_no, depth를 사용해서 insert에 넣는다?) 또는 나중에 한꺼번에 sql 문으로? 
		// request.setAttribute("vo", new BoardDao().findByTitleAndUserno(Long.parseLong(no)));
		BoardVo vo = new BoardDao().findByTitleAndUserno(Long.parseLong(no));
		// System.out.println("===" + vo);
		// no 값이 안들어오는 거야! - 지금 못함
		
		
		// 회원 정보 
		HttpSession session = request.getSession();
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
	     
	    // 가져온 값을 가지고 insert 해버리기
		System.out.println("authUser: " + authUser);
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		// 안넘어옴 
		// no를 가지고 insert
	
		vo.setTitle(title);
		vo.setContents(content);
		// db에서 조회해온 값이 인증된 vo와 같은지 확인?
		// 회원이면 null이 아님 -> no
		vo.setUserNo(authUser.getNo());
		System.out.println("확인 !! "+ vo);
		new BoardDao().reply(vo);  // 
		
		// redirection => list 
		response.sendRedirect(request.getContextPath() + "/board");
		
	}

}
