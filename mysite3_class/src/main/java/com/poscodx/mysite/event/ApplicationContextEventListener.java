package com.poscodx.mysite.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.poscodx.mysite.service.SiteService;
import com.poscodx.mysite.vo.SiteVo;

public class ApplicationContextEventListener {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshEvent() {
		System.out.println("---- Context Refreshed Event Recived ---");  // 처음에 초기화 될 때, 빈 설정을 마친 후, 여기로 들어오는지 확인
	
		SiteService siteService = applicationContext.getBean(SiteService.class);
		SiteVo vo = siteService.getProfile();
		
		//applicationContext.containsBean(null)
		
		MutablePropertyValues propertyValues = new MutablePropertyValues(); 
		propertyValues.add("title", vo.getTitle());
		propertyValues.add("profile", vo.getProfile());
		propertyValues.add("welcome", vo.getWelcome());
		propertyValues.add("description", vo.getDescription());
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(SiteVo.class);
		beanDefinition.setPropertyValues(propertyValues);
		
		
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)applicationContext.getAutowireCapableBeanFactory();
		registry.registerBeanDefinition("site", beanDefinition);  // 빈 등록
	}
}
