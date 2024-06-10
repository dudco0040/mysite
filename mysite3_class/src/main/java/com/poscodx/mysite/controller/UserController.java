package com.poscodx.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.security.AuthUser;
import com.poscodx.mysite.service.UserService;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	// 회원 가입
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(UserVo vo) {
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess", method=RequestMethod.GET)  // redirect 
	public String joinsuccess() {
		return "user/joinsuccess";
	}
	
	// 로그인
	@RequestMapping(value= "/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	
//	@RequestMapping(value = "/login", method=RequestMethod.POST)
//	public String login(HttpSession session, UserVo vo, Model model) {
//		// login 처리- Atheutication
//		UserVo authUser = userService.getUser(vo.getEmail(), vo.getPassword());
//		
//		if(authUser == null) {
//			model.addAttribute("email", vo.getEmail());
//			model.addAttribute("reusult", "fail");
//			
//			return "user/login";
//		}
//		
//		// login 처리
//		session.setAttribute("authUser", authUser);
//		
//		return "redirect:/";
//	}
	
//	// 로그아웃
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		
//		return "redirect:/";
//	}
//	
	
	// 정보 수정
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {

		UserVo vo = userService.getUser(authUser.getNo());
		model.addAttribute("userVo", vo);

		return "user/update";
	}
	
	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpSession session, UserVo vo) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");
//		if(authUser==null) {
//			return "redirect:/";   // 비정상적인 접근
//		}
		////////////////////// 보안 - 횡단 관심

		vo.setNo(authUser.getNo());
		userService.update(vo);
		
		authUser.setName(vo.getName());
		
		return "redirect:/user/update";
	}
}