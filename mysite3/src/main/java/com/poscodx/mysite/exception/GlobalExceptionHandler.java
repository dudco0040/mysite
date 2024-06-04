package com.poscodx.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// exception 발생 시, 실행되는 코드
public class GlobalExceptionHandler {
	
	// Advice
	@ExceptionHandler(Exception.class)  // Exception의 종류
	public String handler(Exception e, Model model) {
		// 1. 로깅(logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		System.out.println(errors.toString());   // console에 출력
		
		// 2. 사과(종료)
		model.addAttribute("error", errors.toString());
		return "errors/exception";
	}
}
