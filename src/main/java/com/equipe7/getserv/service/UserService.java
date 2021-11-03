package com.equipe7.getserv.service;

import java.util.List;

import com.equipe7.getserv.model.RoleEntity;
import com.equipe7.getserv.model.UserEntity;

public interface UserService {
	UserEntity saveUser(UserEntity user);
	//RoleEntity createRole(RoleEntity role);
	
	void addRoleToUser(String username, String roleName);
	UserEntity getUser(String username);
	//List<UserEntity> getUsers();
	
//	String create
}
