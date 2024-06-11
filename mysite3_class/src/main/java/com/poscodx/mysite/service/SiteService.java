package com.poscodx.mysite.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.poscodx.mysite.repository.SiteRepository;
import com.poscodx.mysite.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	
	// 현재 site 보이게 
	public SiteVo getProfile() {
		return siteRepository.find();
	}
	
	// 현재 site에서 수정
	public void updateSite(SiteVo vo) {
		siteRepository.update(vo);
	}
}
