package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.mysite.security.Auth;
import com.poscodx.mysite.service.FileUploadService;
import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;


@Controller
@Auth(role="ADMIN")
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private FileUploadService fileuploadService;
	
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = siteService.getProfile();
		
		model.addAttribute("siteVo", vo);
		
		return "admin/main";
	}

	// 정보 수정
	@RequestMapping(value = "/main/update", method=RequestMethod.POST)
	public String update(SiteVo vo, @RequestParam(value="file") MultipartFile file) {
		
		String profile = fileuploadService.restore(file);
		if(profile != null) {
			vo.setProfile(profile);
		}

		System.out.println("## vo: " + vo);
		System.out.println("## profile: " + profile);
		siteService.updateSite(vo);
		servletContext.setAttribute("sitevo", vo);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
}
