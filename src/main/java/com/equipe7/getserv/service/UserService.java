package com.equipe7.getserv.service;

import java.util.List;
import com.equipe7.getserv.model.UserEntity;

public interface UserService {
	List<UserEntity> getUsers();
	
	UserEntity saveUser(UserEntity user);
	UserEntity getUser(String username);
	
	void addRoleToUser(String username, String roleName);
	void addRoleToUser(UserEntity user, String roleName);
}
