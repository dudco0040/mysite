package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.GuestBookRepository;
import com.poscodx.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestBookRepository guestbookRepository;
	
	// 리스트 보기 
	public List<GuestBookVo> getContentsList(){
		return guestbookRepository.findAll();
	}
	
	// 글 삭제 
	public void daleteContents(GuestBookVo vo) {
		guestbookRepository.delete(vo);
	}
	
	// 글 작성
	public void addContents(GuestBookVo vo) {
		guestbookRepository.insert(vo);
	}
	
}
