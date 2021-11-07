package com.equipe7.getserv.controller.form;

public class ProfileForm {

	protected String username;
	protected String name;
	protected String about;
	protected String imgUrl;
	protected Integer rate; // «« fictício
	
	public ProfileForm() {
		super();
	}
	
	public ProfileForm(String username, String name, String about, String imgUrl, Integer rate) {
		super();
		this.username = username;
		this.name = name;
		this.about = about;
		this.imgUrl = imgUrl;
		this.rate = rate;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public Integer getRate() {
		return rate;
	}
	
	public void setRate(Integer rate) {
		this.rate = rate;
	}
}
