package com.poscodx.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;
import com.poscodx.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	// 글 목록 보기(list)
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model, @RequestParam(value="page", required=true, defaultValue="") String page) {
		
		// 페이지(Pager)
		int currentPage = 1;   // 현재 페이지
		if (!page.isEmpty()) {
		       try {
		           currentPage = Integer.parseInt(page);
		       } catch (NumberFormatException e) {
		           currentPage = 1;
		       }
		   }
		
	    // 글 목록 보기
		Map<String, Object> map = boardService.getList(currentPage);
		
//        System.out.println("Map contents: " + map);
//        System.out.println("List contents: " + map.get("list"));
		
		model.addAttribute("map", map);

		
		return "board/list";
	}
	
	
	// 글 보기(view)
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model) {
		BoardVo vo = boardService.view(no);
		
		model.addAttribute("vo", vo);
		
		return "board/view"; 
	}
	
	
	// 글쓰기(insert)
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(@RequestParam(required = false) Long no, 
            				@RequestParam(required = false, defaultValue = "false") boolean isReply, 
            				Model model) {
		
		model.addAttribute("no", no);
		model.addAttribute("isReply", isReply);
		return "board/write";		// writeform으로 이동 
	}
	
	//write
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@RequestParam(value="title", required=true, defaultValue="") String title, 
						@RequestParam(value="contents", required=true, defaultValue="") String contents,
						@RequestParam(required = false, defaultValue = "false") boolean isReply,  // 이 부분 수정
						@RequestParam(value = "no", required = false) Long no,
						HttpSession session) {
		
		
		// Access Control
        UserVo authUser = (UserVo) session.getAttribute("authUser");
        System.out.println(authUser.getNo());
        if (authUser.getNo() == null) {
            return "redirect:/board";
        }
        
        
		BoardVo vo = new BoardVo();
		System.out.println(title + "," + contents + "," + isReply + "," + no);
		
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(authUser.getNo());   // 비회원일 경우, 달 수 없음 - 글쓰기 폼에 아예 들어갈수 없어야함 
		
		if(isReply && no != null) {
			// 답글
			vo.setgNo(no);
			boardService.reply(vo);
		} else {
			// 본문
			System.out.println("본문~~");
			boardService.write(vo);
			
		}
		
		return "redirect:/board";
	}
	
	// 글 삭제
//	@RequestMapping(value="delete", method=RequestMethod.POST)
//	public String delete() {
//		boardService.delete();   // no, userNo
//		return "redirect:/board/list";
//	}
		
}
