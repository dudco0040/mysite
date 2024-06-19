package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public void join(UserVo vo) {
		userRepository.insert(vo);
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByNoAndPassword(email, password);
	}

	public UserVo getUser(Long no) {
		return userRepository.findByNo(no);
	}
	
	public UserVo getUser(String eamil) {
		return userRepository.findByEmail(eamil);
	}
	
	public void update(UserVo vo) {
		userRepository.update(vo);
	}
		
}