package com.poscodx.mysite.initializer;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.poscodx.mysite.config.AppConfig;
import com.poscodx.mysite.config.WebConfig;

public class MySiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {   // 컨테이너 초기 설정 파일(class)

		return new Class<?>[] {AppConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {  // webapplication 설정 파일

		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected Filter[] getServletFilters() {
		
		return new Filter[] {new CharacterEncodingFilter("UTF-8")};
	}

	@Override
	protected String[] getServletMappings() {   // Servlet Mapping: 서블릿은 내가 만들테니까 어떤 url에 매핑할 것인지 알려줘!

		return new String[] {"/"};
	}

}
