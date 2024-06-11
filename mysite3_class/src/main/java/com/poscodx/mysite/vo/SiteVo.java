package com.poscodx.mysite.vo;

public class SiteVo {
	private Long no;
	private String title;
	private String welcome;
	private String proile;
	private String description;
	@Override
	public String toString() {
		return "SiteVo [no=" + no + ", title=" + title + ", welcome=" + welcome + ", proile=" + proile
				+ ", description=" + description + "]";
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWelcome() {
		return welcome;
	}
	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}
	public String getProile() {
		return proile;
	}
	public void setProile(String proile) {
		this.proile = proile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}	
