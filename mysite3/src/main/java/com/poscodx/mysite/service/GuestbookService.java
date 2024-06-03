package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	// 리스트 보기 
	public List<GuestBookVo> getContentsList(){
		return null;
	}
	
	// 글 삭제 
	public void daleteContents(Long no, String password) {
		
	}
	
	// 글 작성
	public void addContents(GuestBookVo vo) {
		
	}
	
}
