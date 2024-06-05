package com.poscodx.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	// 목록 (list)
	public List<BoardVo> getList(int currentPage, int recordsPerPage) {
		return boardRepository.getList(currentPage, recordsPerPage);
	}
	
	public int countRecords() {
		return boardRepository.countRecords();
	}
	
	// 글 보기(view)
	public BoardVo view(Long no) {
		return boardRepository.findByTitleAndUsernoView(no);
	}

	// 글 쓰기(write)
	public void write(BoardVo vo) {
		boardRepository.insert(vo);	
	}
	
	// 답글 쓰기(reply)
	public void reply(BoardVo vo) {
		boardRepository.reply(vo);
	}

	// 글 삭제(delete)
//	public void delete() {
//		boardRepository.delete(no, userNo);
//	}
	
}
