package com.equipe7.getserv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.equipe7.getserv.repository.UserRepository;
import com.equipe7.getserv.resource.Table;
//import com.equipe7.getserv.service.UserService;

@RestController
@RequestMapping("/api")
public class ApiController {

//	@Autowired
//	private UserService userServ;
	
	@Autowired
	private UserRepository userRepo;

	@GetMapping("/check/usernames")
	public ResponseEntity<Boolean> usernameResponse(@RequestParam String username){
		if (Table.getUsernames().size() == 0)
			Table.reset(userRepo);
		return ResponseEntity.ok(Table.getUsername(username));
	}
	
	public Boolean usernameExisting(@RequestParam String username){
		if (Table.getUsernames().size() == 0)
			Table.reset(userRepo);
		return Table.getUsername(username);
	}
	
}
