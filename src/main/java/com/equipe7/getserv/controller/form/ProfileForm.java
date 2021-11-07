package com.equipe7.getserv.controller.form;

import com.equipe7.getserv.model.UserEntity;

public class ProfileForm {

	protected String username;
	protected String name;
	protected String description;
	protected String imgUrl;
	protected Integer rating; // «« fictício
	protected String tagline;
	
	public ProfileForm() {
		super();
	}
	
	public ProfileForm(String username, String name, String description, String imgUrl, Integer rating, String tagline) {
		super();
		this.username = username;
		this.name = name;
		this.description = description;
		this.imgUrl = imgUrl;
		this.rating = rating;
		this.tagline = tagline;
	}
	
	public ProfileForm(UserEntity user) {
		setUsername(user.getUsername());
		setName(user.getRegister().getName());
		setDescription(user.getProfile().getAbout());
		setImgUrl(user.getProfile().getPictureURL());
		setRating(5);
		setTagline(user.getProfile().getTagline());
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public Integer getRating() {
		return rating;
	}
	
	public void setRating(Integer rate) {
		this.rating = rate;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}
}
