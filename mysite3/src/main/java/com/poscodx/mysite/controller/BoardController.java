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
		
        // System.out.println("Map contents: " + map);
        // System.out.println("List contents: " + map.get("list"));
		
		model.addAttribute("map", map);

		
		return "board/list";
	}
	
	
	// 글 보기(view)
	@RequestMapping(value="/view/{no}", method=RequestMethod.GET)
	public String view(@PathVariable("no") Long no, Model model,
						HttpSession session) {
		
		// Access Control
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        // 글보기는 모두가 가능해야함 -> authUser값은 jsp 파일에서 판단 하니까 여기서는 전달만.. 회원 정보만 전달 
        // auth 는 어떻게 보내?  로그인을 안하면 null, 하면 넘겨
        
        
		BoardVo vo = boardService.view(no);
		
		// jsp 파일로 전달 
		model.addAttribute("vo", vo);
		System.out.println(vo);		// vo에 값이 다 안들어감 user_no = null -> 수정이 되지 않음 
		if(authUser != null) {
    		model.addAttribute("authUser", authUser);
    		System.out.println(authUser);
        }
		
		// auth 이 부분을 찾아보자.. 그리고 userNo가 어디서 나오는지...
		
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
	
	// write
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
	
	// 글 수정(update)
	@RequestMapping(value="modify/{no}", method=RequestMethod.GET)
	public String update(@PathVariable("title") String title, 
						@PathVariable("contents") String contents,
						@PathVariable("no") Long no,
						Model model) {
		
		// jsp 파일에 전달 
		model.addAttribute("no");
		model.addAttribute("title");
		model.addAttribute("contents");
		
		return "board/modify";
	}
	
	
	@RequestMapping(value="modify/{no}", method=RequestMethod.POST)
	public String update(@RequestParam(value="title", required=true, defaultValue="") String title, 
						@RequestParam(value="contents", required=true, defaultValue="") String contents,
						@PathVariable("no") Long no,
						Model model,
						HttpSession session) {
		
		
		
		// 한꺼번에 넘기기
		BoardVo vo = new BoardVo();
		//vo.setUserNo(authUser.getNo());
		vo.setTitle(title);
		vo.setContents(contents);
		
		boardService.update(vo);
		
		return "redirect:/board/list";
		
	}
	
	// 글 삭제
//	@RequestMapping(value="delete", method=RequestMethod.POST)
//	public String delete() {
//		boardService.delete();   // no, userNo
//		return "redirect:/board/list";
//	}
		
}
