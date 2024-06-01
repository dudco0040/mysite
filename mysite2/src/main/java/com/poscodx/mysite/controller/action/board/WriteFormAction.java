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
		// 본문인지 답글인지 확인 
        String reply = request.getParameter("reply");
        boolean isReply = "TRUE".equalsIgnoreCase(reply);
        String no = request.getParameter("no");
        System.out.println("reply(y/n): " + isReply);
        System.out.println("no:" + no);  // null 값이 들어와도 넘겨주고 안쓰면 되잖아 
        
        // 본문/답글 나누기
        // "isReply"로 전달 
        request.setAttribute("isReply", isReply);
        request.setAttribute("no", no);  // null 일 경우 Long.parseLong(no) 변환이 안되서 문제 ~
        request
        	.getRequestDispatcher("/WEB-INF/views/board/write.jsp")
        	.forward(request, response);

	}

}
