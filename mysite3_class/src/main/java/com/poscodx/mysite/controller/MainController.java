package com.poscodx.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
public class MainController {
	private SiteService siteService;
	
	public MainController(SiteService siteService) {
		this.siteService = siteService;
	}
	
	@RequestMapping({"/", "/main"})
	public String index(Model model) {
		SiteVo vo = siteService.getProfile();
		
		model.addAttribute("siteVo", vo);
		
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/msg01")
	public String message01() {
		return "Hello World!";
	}
	
	@ResponseBody
	@RequestMapping("/msg02")   // msg02?name=영채
	public String message02(String name) {
		return "안녕~" + name;
	}
	
	@ResponseBody
	@RequestMapping("/msg03")  // 에러발생 시키기
	public Object message03() {
		UserVo vo = new UserVo();
		vo.setNo(1L);
		vo.setName("둘리");
		vo.setEmail("dooly@gmail.com");	
		
		return vo;
	}
}