package com.poscodx.mysite.controller.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscodx.mysite.controller.ActionServlet.Action;
import com.poscodx.mysite.vo.BoardVo;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 댓글인지 답글인지 확인 
        String reply = request.getParameter("reply");
        boolean isReply = "TRUE".equalsIgnoreCase(reply);
        String no = request.getParameter("no");
        System.out.println("reply(y/n): " + isReply);
        System.out.println("no:" + no);

        // "isReply"로 전달 
        request.setAttribute("isReply", isReply);
        request.setAttribute("no", Long.parseLong(no));
        request
        	.getRequestDispatcher("/WEB-INF/views/board/write.jsp")
        	.forward(request, response);

	}

}
