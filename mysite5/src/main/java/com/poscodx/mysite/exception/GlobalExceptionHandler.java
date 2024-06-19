package com.poscodx.mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poscodx.mysite.dto.JsonResult;

@ControllerAdvice
// exception 발생 시, 실행되는 코드
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);
	// Advice
	@ExceptionHandler(Exception.class)  // Exception의 종류
	public void handler(
			HttpServletRequest request,
			HttpServletResponse response,
			Exception e) throws Exception{
		
		// 1. 로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		logger.error(errors.toString());
		
		//System.out.println(errors.toString());   // console에 출력  <appender-ref ref="consoleAppender" /> 추가
		
		// 2. 요청 구분(html/json)
		// json 요청: request header에 application/json (O)
		// html 요청: request header에 application/json (X)
		String accept = request.getHeader("accept");
		if(accept.matches(".*application/json.*")) {  // ".*": 모든 문자(정규 표현식)
			// 3. json 응답
			JsonResult jsonResult = JsonResult.fail(errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);
			
			response.setStatus(HttpServletResponse.SC_OK);
			
			response.setContentType("application/json; charset=utf-8");
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();
			
		} else {  
			// json이 아닌 경우 
			// 4. 사과(정상 종료)
			if(e instanceof NoHandlerFoundException) {
				request
				.getRequestDispatcher("/WEB-INF/views/errors/404.jsp")
				.forward(request, response);
				
			} else {
				request.setAttribute("error", errors.toString());
				request
				.getRequestDispatcher("/WEB-INF/views/errors/exception.jsp")
				.forward(request, response);
			}
			
		}

	}
}
