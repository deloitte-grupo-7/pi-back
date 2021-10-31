package com.equipe7.getserv.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.controller.form.RoleToUserForm;
import com.equipe7.getserv.model.RoleEntity;
import com.equipe7.getserv.model.UserEntity;
import com.equipe7.getserv.service.UserService;

@RestController
@RequestMapping("")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/get/users")
	public ResponseEntity<List<UserEntity>> getUsers(){
		return ResponseEntity.ok().body(userService.getUsers());
	}

	@PostMapping("/post/user")
	public ResponseEntity<UserEntity> postUser(UserEntity user){
		return ResponseEntity.created(null).body(userService.createUser(user));
	}

	@PostMapping("/post/role")
	public ResponseEntity<RoleEntity> postUser(RoleEntity role){
		return ResponseEntity.created(null).body(userService.createRole(role));
	}

	@PostMapping("/post/addrole")
	public ResponseEntity<?> postToUser(RoleToUserForm form){
		userService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}
	
}
