package com.equipe7.getserv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.repository.UserRepository;
import com.equipe7.getserv.resource.Table;
import com.equipe7.getserv.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepository repository;
	private final UserService userServ;
	
	AdminController(UserService userServ){
		super();
		this.userServ = userServ;
	}

	@GetMapping("/addrole/{user}:{role}")
	public ResponseEntity<String> addRoleToUser(@PathVariable String user, @PathVariable String role){
		userServ.addRoleToUser(user, "ROLE_" + role);
		return ResponseEntity.ok(user + " : " + role);
	}
	
	@GetMapping("/tables/reset")
	public void startTableLists() {
		Table.reset(repository);
	}
}
