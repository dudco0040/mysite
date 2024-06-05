package com.poscodx.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.mysite.service.BoardService;
import com.poscodx.mysite.vo.BoardVo;

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
	    int recordsPerPage = 8;  // 한 페이지당 보여줄 글의 개수
	    int totalRecords = boardService.countRecords();   // 전체 글의 개수
	    int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);  // 전체 페이지 개수
	    
	    // 글 목록 보기
		List<BoardVo> list = boardService.getList(currentPage, recordsPerPage);
		//System.out.println(currentPage + "," + recordsPerPage);
		
		
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		
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
		return "board/write";
	}
	
	//write
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@RequestParam(value="title", required=true, defaultValue="") String title, 
						@RequestParam(value="contents", required=true, defaultValue="") String contents,
						@PathVariable("isReply") boolean isReply,  // 이 부분 수정
						@PathVariable(value = "no", required = false) Long no) {
		
		BoardVo vo = new BoardVo();
		System.out.println(title + "," + contents + "," + isReply + "," + no);
		
		vo.setTitle(title);
		vo.setContents(contents);
		
		if(isReply && no != null) {
			// 답글
			vo.setgNo(no);
			boardService.reply(vo);
		} else {
			// 본문
			boardService.write(vo);
		}
		
		return "redirect:/board/list";
	}
	
	// 글 삭제
//	@RequestMapping(value="delete", method=RequestMethod.POST)
//	public String delete() {
//		boardService.delete();   // no, userNo
//		return "redirect:/board/list";
//	}
		
}
