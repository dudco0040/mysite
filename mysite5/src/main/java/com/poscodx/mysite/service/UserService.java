package com.poscodx.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poscodx.mysite.repository.UserRepository;
import com.poscodx.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void join(UserVo vo) {
		String encode_password = passwordEncoder.encode(vo.getPassword());
		System.out.println(encode_password);
		
		vo.setPassword(encode_password);
		userRepository.insert(vo);
	}

	public UserVo getUser(String email, String password) {
		return userRepository.findByNoAndPassword(email, password);
	}

	public UserVo getUser(Long no) {
		return userRepository.findByNo(no);
	}
	
	public UserVo getUser(String eamil) {
		return userRepository.findByEmail(eamil, UserVo.class);
	}
	
	public void update(UserVo vo) {
		System.out.println("service -> encoding   ");
		vo.setPassword(vo.getPassword().equals("") ? "":passwordEncoder.encode(vo.getPassword()));
		System.out.println("## vo: " + vo);
		userRepository.update(vo);
	}
		
}