package com.equipe7.getserv.controller.form;

import com.equipe7.getserv.model.UserEntity;

public class AccountForm {

	private String name;
	private String username;
	private String email;
	private String description; //<< about
	private String password;
	
	public AccountForm() {
		super();
	}
	
	public AccountForm(String name, String username, String email, String description, String password) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.description = description;
		this.password = password;
	}
	
	public AccountForm(UserEntity user) {
		setName(user.getRegister().getName());
		setUsername(user.getUsername());
		setEmail(user.getRegister().getEmail());
		setDescription(user.getProfile().getAbout());
		setPassword("************");
	}
	
	public void updateUser(UserEntity user) {
		user.setUsername(username);
		user.getProfile().setAbout(description);
		user.getRegister().setEmail(email);
		user.getRegister().setName(name);
		user.setPassword(password);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
