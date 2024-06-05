package com.poscodx.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.BoardRepository;
import com.poscodx.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	// 전체 글의 개수 계산 메서드 추가
		public int countRecords() {
			return boardRepository.countRecords();
		}
	
	// 목록 (list)
	public Map<String, Object> getList(int currentPage) {
		List<BoardVo> list = null;
		Map<String, Object> map = new HashMap<>();  // Map 초기화
		
		
		int recordsPerPage = 8;  // 한 페이지당 보여줄 글의 개수
		int totalRecords = countRecords();  // 전체 글의 개수 가져오기
		int totalPages = (int) Math.ceil(totalRecords * 1.0 / recordsPerPage);
		list = boardRepository.getList(currentPage, recordsPerPage);
		
		map.put("list", list);
		map.put("currentPage", currentPage);
		map.put("recordsPerPage", recordsPerPage);
		map.put("totalPages", totalPages);
		
		return map;  // sql 실행 후 결과 
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
