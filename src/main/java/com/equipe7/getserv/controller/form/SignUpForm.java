package com.equipe7.getserv.controller.form;

import java.util.Date;

public class SignUpForm {

	private Long id;
	private String username;
	private String password;
	private String passconf;
	
	private String name;
	private String cpf;
	private String email;
    private Date birthday = new java.sql.Date(System.currentTimeMillis());
	
    public SignUpForm (){
    	
    }
    
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassconf() {
		return passconf;
	}
	public void setPassconf(String passconf) {
		this.passconf = passconf;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
    
}
