package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.GuestbookService;
import com.poscodx.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestbookService guestbookService;
	
	// 리스트 보기
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		List<GuestbookVo> contents = guestbookService.getContentsList();
		model.addAttribute("list", contents);
		return "guestbook/list";
	}
	
	// 글 작성
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.addContents(vo);
		
		return "redirect:/guestbook/list";
	}
	
	// 글 삭제
	// deleteform
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		
		return "/guestbook/delete";
	}

	//delete
	@RequestMapping(value="/delete/{no}", method=RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value="password", required=true, defaultValue="") String password) {
		System.out.println(no + "," + password);
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		
		guestbookService.daleteContents(vo);
		
		return "redirect:/guestbook/list";
	}

}
