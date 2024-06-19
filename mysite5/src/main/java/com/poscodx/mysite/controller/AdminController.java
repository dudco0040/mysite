package com.poscodx.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
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
		
		SiteVo site = applicationContext.getBean(SiteVo.class);
		// Properties를 수정
//		site.setTitle(vo.getTitle());
//		site.setWelcome(vo.getWelcome());
//		site.setProfile(vo.getProfile());
//		site.setDescription(vo.getDescription());
		BeanUtils.copyProperties(vo, site);
		
		return "admin/main";
	}

	// 정보 수정
	@RequestMapping(value = "/main/update", method=RequestMethod.POST)
	public String update(SiteVo vo, @RequestParam(value="file") MultipartFile file) {
		
		String profile = fileuploadService.restore(file);
		if(profile != null) {		// 사용자가 파일 업로드를 안할 경우, 기존의 정보를 불러옴(null로 수정되는 것을 막기 위해)
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
