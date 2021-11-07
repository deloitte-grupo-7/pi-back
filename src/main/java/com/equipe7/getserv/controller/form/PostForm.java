package com.equipe7.getserv.controller.form;

public class PostForm {

	private Long id;
	private String title;
	private String imgUrl;
	private String description;
	
	public PostForm() {
		super();
	}
	
	public PostForm(Long id, String title, String imgUrl, String description) {
		super();
		this.id = id;
		this.title = title;
		this.imgUrl = imgUrl;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}	
}
