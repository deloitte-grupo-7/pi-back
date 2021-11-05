package com.equipe7.getserv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.controller.form.RoleToUserForm;
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


	@GetMapping("/addrole/{user}/{role}")
	public ResponseEntity<RoleToUserForm> addRoleToUser(RoleToUserForm form){
		userServ.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok(form);
	}
	
	@GetMapping("/tables/reset")
	public void startTableLists() {
		Table.reset(repository);
	}
}
