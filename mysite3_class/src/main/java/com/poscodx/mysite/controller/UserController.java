package com.poscodx.mysite.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		// model.addAttribute("userVo", vo);
		
		
		if(result.hasErrors()) {  // 에러가 있는지 확인. 에러가 있을 경우 다시 join form으로 이동 
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error: list) {
//				System.out.println(error);
//			}
			
			Map<String, Object>map = result.getModel();
			
			model.addAllAttributes(map);  // map.key에 errors가 존재 

			return "user/join";
		}
		
		// validation이 성공하면 실행 
		//userService.join(vo);
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
	public String update(@AuthUser UserVo authUser, UserVo vo) {

//		UserVo authUser = (UserVo)session.getAttribute("authUser");
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