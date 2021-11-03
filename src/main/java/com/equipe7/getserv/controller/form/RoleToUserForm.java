package com.equipe7.getserv.controller.form;

public class RoleToUserForm {
	
	private String username;
	private String roleName;
	
	
	
	public RoleToUserForm() {
		super();
	}
	
	public RoleToUserForm(String username, String roleName) {
		super();
		this.username = username;
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
